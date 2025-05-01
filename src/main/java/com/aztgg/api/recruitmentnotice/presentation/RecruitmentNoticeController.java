package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.RecruitmentNoticeService;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recruitment-notices")
public class RecruitmentNoticeController implements RecruitmentNoticeApi {

    private final RecruitmentNoticeService recruitmentNoticeService;

    @Override
    @GetMapping("/{recruitmentNoticeId}/redirect")
    public void redirectByRecruitmentNotice(HttpServletResponse response, @PathVariable Long recruitmentNoticeId) throws IOException {
        GetRecruitmentNoticeDto responseDto = recruitmentNoticeService.incrementViewCountAndGetRecruitmentNoticeRedirection(recruitmentNoticeId);
        response.sendRedirect(responseDto.url());
    }

    @Override
    @GetMapping("/redirections")
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(@RequestParam("companyCode") String companyCode,
                                                                                              @RequestParam(value = "category", required = false) String category,
                                                                                              @RequestParam("page") int page,
                                                                                              @RequestParam("pageSize") int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return recruitmentNoticeService.getRecruitmentNoticeRedirectionList(companyCode, category, pageRequest);
    }
}
