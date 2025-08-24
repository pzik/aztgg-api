package com.aztgg.api.hotissue.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.application.dto.CreateCommentToHotIssueRequestDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssuesResponseDto;
import com.aztgg.api.hotissue.domain.HotIssueDomainService;
import com.aztgg.api.hotissue.domain.exception.HotIssueNotFoundDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HotIssueFacadeService {

    private final HotIssueDomainService hotIssueDomainService;

    public void commentToHotIssue(Long hotIssueId, String ip, CreateCommentToHotIssueRequestDto payload) {
        try {
            hotIssueDomainService.commentToHotIssue(hotIssueId, ip, payload.anonymousName(), payload.content());
        } catch (HotIssueNotFoundDomainException e) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "not found hotIssue");
        }
    }

    @Transactional(readOnly = true)
    public GetHotIssuesResponseDto getActivatedHotIssues() {
        List<GetHotIssueResponseDto> hotIssues = hotIssueDomainService.getActivatedHotIssues().stream()
                .map(a -> GetHotIssueResponseDto.fromLimitCommentDesc(a, 3))
                .toList();
        return new GetHotIssuesResponseDto(hotIssues);
    }

    @Transactional(readOnly = true)
    public GetHotIssueResponseDto getHotIssueById(Long hotIssueId) {
        return hotIssueDomainService.findHotIssueById(hotIssueId)
                .map(GetHotIssueResponseDto::from)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found hotissue"));
    }
}
