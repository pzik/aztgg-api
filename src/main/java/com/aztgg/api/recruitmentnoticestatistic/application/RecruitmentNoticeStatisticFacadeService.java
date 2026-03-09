package com.aztgg.api.recruitmentnoticestatistic.application;

import com.aztgg.api.global.asset.PredefinedCompany;
import com.aztgg.api.global.asset.PredefinedStandardCategory;
import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.global.logging.AppLogger;
import com.aztgg.api.recruitmentnoticestatistic.application.dto.GetDailyStatisticCacheDto;
import com.aztgg.api.recruitmentnoticestatistic.application.dto.GetDailyStatisticsResponse;
import com.aztgg.api.recruitmentnoticestatistic.domain.RecruitmentNoticeStatistic;
import com.aztgg.api.recruitmentnoticestatistic.domain.RecruitmentNoticeStatisticDomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentNoticeStatisticFacadeService {

    private final RecruitmentNoticeStatisticDomainService recruitmentNoticeStatisticDomainService;
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;

    private static final String DAILY_STATISTICS_CACHE = "dsk";
    private static final String DAILY_STATISTICS_CACHE_KEY = "%s:%s:%s"; // date, standardCategoryCode, companyCodes

    /**
     * companyCodes는 비어있을 경우 전체로 간주
     * @param startAt
     * @param endAt
     * @param standardCategoryCode
     * @param companyCodes
     * @return
     */
    public GetDailyStatisticsResponse getDailyStatistics(LocalDate startAt, LocalDate endAt,
                                                         String standardCategoryCode, Collection<String> companyCodes) {
        if (endAt.isEqual(LocalDate.now()) || endAt.isAfter(LocalDate.now())) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "하루 전 데이터부터 조회가 가능합니다.");
        }

        PredefinedStandardCategory predefinedStandardCategory = PredefinedStandardCategory.fromCode(standardCategoryCode);
        Set<PredefinedCompany> predefinedCompanies = companyCodes.stream()
                .map(PredefinedCompany::fromCode)
                .filter(PredefinedCompany::isNotUnknown)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (predefinedCompanies.isEmpty()) {
            predefinedCompanies = EnumSet.allOf(PredefinedCompany.class).stream()
                    .sorted()
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        List<GetDailyStatisticCacheDto> resultItems = new ArrayList<>();
        String companyCodesCacheKey = String.join(",", predefinedCompanies.stream().map(Enum::name).toList());

        // startAt ~ endAt까지 캐시 조회
        for (LocalDate date = startAt; !date.isAfter(endAt); date = date.plusDays(1)) {
            String cacheKey = String.format(DAILY_STATISTICS_CACHE_KEY, date, standardCategoryCode, companyCodesCacheKey);
            Cache cache = cacheManager.getCache(DAILY_STATISTICS_CACHE);
            String cacheOrigin = cache.get(cacheKey, String.class);
            if (cacheOrigin == null) {
                break;
            }

            try {
                GetDailyStatisticCacheDto data = objectMapper.readValue(cacheOrigin, GetDailyStatisticCacheDto.class);
                resultItems.add(data);
            } catch (Exception e) {
                AppLogger.errorLog(e.getMessage(), e);
            }
        }
        // 마지막 캐시 저장 일자 확인 후 이후 시점부터 디비 조회 후 캐시적재
        LocalDate lastCached = startAt;
        if (!resultItems.isEmpty()) {
            lastCached = resultItems.getLast().date();
        }

        if (lastCached.isBefore(endAt)) {
            List<RecruitmentNoticeStatistic> search = recruitmentNoticeStatisticDomainService.findAll(lastCached, endAt, predefinedStandardCategory, predefinedCompanies);
            // 캐시 형태로 변환 및 캐시 저장
            Map<LocalDate, Integer> dailyCountMap = search.stream()
                    .collect(Collectors.groupingBy(RecruitmentNoticeStatistic::getCreatedAt, Collectors.summingInt(RecruitmentNoticeStatistic::getCount)));

            Cache cache = cacheManager.getCache(DAILY_STATISTICS_CACHE);
            for (LocalDate date = lastCached; !date.isAfter(endAt); date = date.plusDays(1)) {
                Integer count = dailyCountMap.get(date);
                GetDailyStatisticCacheDto newDto = new GetDailyStatisticCacheDto(date, Objects.requireNonNullElse(count, 0));

                // 이전 캐시값과 병합
                final LocalDate finalDate = date;
                resultItems.removeIf(d -> d.date().isEqual(finalDate));
                resultItems.add(newDto);

                // 캐시 저장
                String cacheKey = String.format(DAILY_STATISTICS_CACHE_KEY, date, standardCategoryCode, companyCodesCacheKey);
                try {
                    cache.put(cacheKey, objectMapper.writeValueAsString(newDto));
                } catch (Exception e) {
                    AppLogger.errorLog(e.getMessage(), e);
                }
            }
        }

        return new GetDailyStatisticsResponse(resultItems);
    }
}
