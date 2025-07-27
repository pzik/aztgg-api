package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.dto.request.NicknameUpdateRequest;
import com.aztgg.api.auth.application.dto.response.UserResponse;
import com.aztgg.api.auth.application.UserService;
import com.aztgg.api.global.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        UserResponse userResponse = userService.getCurrentUser(principal.getUserId());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/nickname")
    public ResponseEntity<UserResponse> updateNickname(@AuthenticationPrincipal CustomUserPrincipal principal, @Valid @RequestBody NicknameUpdateRequest request) {
        UserResponse userResponse = userService.updateNickname(principal.getUserId(), request.nickname());
        return ResponseEntity.ok(userResponse);
    }
}
