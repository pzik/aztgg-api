package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.RecruitmentNoticeService;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListRequestDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionsByRankDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recruitment-notices")
public class RecruitmentNoticeController implements RecruitmentNoticeApi {

    private final RecruitmentNoticeService recruitmentNoticeService;

    @Override
    @GetMapping("/{recruitmentNoticeId}/redirect")
    public void redirectByRecruitmentNotice(HttpServletResponse response, @PathVariable Long recruitmentNoticeId) throws IOException {
        GetRecruitmentNoticeRedirectionResponseDto responseDto = recruitmentNoticeService.incrementViewCountAndGetRecruitmentNoticeRedirection(recruitmentNoticeId);
        response.sendRedirect(responseDto.url());
    }

    @Override
    @GetMapping("/redirections")
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(@RequestParam(value = "companyCode", required = false) String companyCode,
                                                                                              @RequestParam(value = "category", required = false) String category,
                                                                                              @RequestParam(value = "standardCategory", required = false) String standardCategory,
                                                                                              @RequestParam("page") int page,
                                                                                              @RequestParam("pageSize") int pageSize,
                                                                                              @RequestParam(value = "sort", defaultValue = "startAt,desc") List<String> sort) {

        GetRecruitmentNoticeRedirectionListRequestDto requestDto = GetRecruitmentNoticeRedirectionListRequestDto.builder()
                .category(category)
                .standardCategory(standardCategory)
                .companyCode(companyCode)
                .page(page)
                .pageSize(pageSize)
                .sort(sort)
                .build();
        return recruitmentNoticeService.getRecruitmentNoticeRedirectionList(requestDto);
    }

    @Override
    @GetMapping("/redirections/daily-rank")
    public GetRecruitmentNoticeRedirectionsByRankDto getRecruitmentNoticeRedirectionListByRank(@RequestParam("date") String date) {
        return recruitmentNoticeService.getRecruitmentNoticeRedirectionsByRank(date);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{recruitmentNoticeId}/click")
    public void incrementClickCountByRecruitmentNoticeId(@PathVariable("recruitmentNoticeId") Long recruitmentNoticeId) {
        recruitmentNoticeService.incrementClickCountByRecruitmentNoticeId(recruitmentNoticeId);
    }
}
