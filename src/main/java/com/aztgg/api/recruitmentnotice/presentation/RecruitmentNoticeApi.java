package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface RecruitmentNoticeApi {

    void redirectByRecruitmentNotice(HttpServletResponse response, Long recruitmentNoticeId) throws IOException;

    GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(List<String> companyCodes,
                                                                                       int page,
                                                                                       int pageSize);
}
