package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.recruitmentnotice.application.RecruitmentNoticeFacadeService;
import com.aztgg.api.recruitmentnotice.application.RecruitmentNoticeService;
import com.aztgg.api.recruitmentnotice.application.dto.*;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recruitment-notices")
public class RecruitmentNoticeController implements RecruitmentNoticeApi {

    private final RecruitmentNoticeService recruitmentNoticeService;
    private final RecruitmentNoticeFacadeService recruitmentNoticeFacadeService;

    @Value("${page.error}")
    private String errorPageUrl;

    @Override
    @GetMapping("/{recruitmentNoticeId}/redirect")
    public void redirectByRecruitmentNotice(HttpServletResponse response, @PathVariable Long recruitmentNoticeId) throws IOException {
        try {
            GetRecruitmentNoticeRedirectionResponseDto responseDto = recruitmentNoticeService.incrementViewCountAndGetRecruitmentNoticeRedirection(recruitmentNoticeId);
            response.sendRedirect(responseDto.url());
        } catch (CommonException ce) {
            if (ce.getErrorCode().equals(RecruitmentNoticeErrorCode.BAD_REQUEST_RECRUITMENT_NOTICE_NOT_FOUND)) {
                response.sendRedirect(errorPageUrl + "?reason=%EC%9E%98%EB%AA%BB%EB%90%9C%20%ED%8E%98%EC%9D%B4%EC%A7%80%EB%A1%9C%20%EC%A0%91%EA%B7%BC%ED%96%88%EC%96%B4%EC%9A%94");
                return;
            }
            throw ce;
        }
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

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{recruitmentNoticeId}/hot-issues/comments")
    public void createHotIssueCommentByRecruitmentNoticeId(HttpServletRequest httpServletRequest, @PathVariable("recruitmentNoticeId") Long recruitmentNoticeId,
                                                           @RequestBody @Valid CreateHotIssueCommentByRecruitmentNoticeIdFacadeRequestDto payload) {
        String ip = httpServletRequest.getHeader("x-forwarded-for") != null ?
                httpServletRequest.getHeader("x-forwarded-for").split(",")[0] : httpServletRequest.getRemoteAddr();
        recruitmentNoticeFacadeService.createComment(recruitmentNoticeId, ip, payload);
    }

    @Override
    @GetMapping("/{recruitmentNoticeId}/hot-issues/comments")
    public GetHotIssueByNoticeIdFacadeResponseDto getHotIssueByNoticeId(Long recruitmentNoticeId) {
        return recruitmentNoticeFacadeService.getHotIssueByNoticeId(recruitmentNoticeId);
    }
}
