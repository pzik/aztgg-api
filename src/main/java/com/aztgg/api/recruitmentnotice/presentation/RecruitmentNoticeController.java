package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.RecruitmentNoticeService;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recruitment-notices")
public class RecruitmentNoticeController implements RecruitmentNoticeApi {

    private final RecruitmentNoticeService recruitmentNoticeService;

    @Override
    @GetMapping("/redirections")
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(@RequestParam("companyCode") String companyCode,
                                                                                              @RequestParam(value = "category", required = false) String category,
                                                                                              @RequestParam("page") int page,
                                                                                              @RequestParam("pageSize") int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return recruitmentNoticeService.getRecruitmentNoticeRedirectionList(companyCode, category, pageRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{recruitmentNoticeId}/click")
    public void incrementClickCountByRecruitmentNoticeId(@PathVariable("recruitmentNoticeId") Long recruitmentNoticeId) {
        recruitmentNoticeService.incrementClickCountByRecruitmentNoticeId(recruitmentNoticeId);
    }
}
