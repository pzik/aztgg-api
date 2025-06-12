package com.aztgg.api.hotissue.presentation;

import com.aztgg.api.hotissue.application.HotIssueService;
import com.aztgg.api.hotissue.application.dto.CreateCommentToHotIssueRequestDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssuesResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hot-issues")
public class HotIssueController implements HotIssueApi {

    private final HotIssueService hotIssueService;

    @Override
    @GetMapping("/{hotIssueId}")
    public GetHotIssueResponseDto getHotIssue(@PathVariable("hotIssueId") Long hotIssueId) {
        return hotIssueService.getHotIssueById(hotIssueId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{hotIssueId}/comments")
    public void addComment(HttpServletRequest httpServletRequest, @PathVariable("hotIssueId") Long hotIssueId, @RequestBody @Valid CreateCommentToHotIssueRequestDto payload) {
        String ip = httpServletRequest.getHeader("x-forwarded-for") != null ?
                httpServletRequest.getHeader("x-forwarded-for").split(",")[0] : httpServletRequest.getRemoteAddr();
        hotIssueService.commentToHotIssue(hotIssueId, ip, payload);
    }

    @Override
    @GetMapping("/activated-list")
    public GetHotIssuesResponseDto getActivateHotIssues() {
        return hotIssueService.getActivatedHotIssues();
    }
}
