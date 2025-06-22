package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.dto.response.UserResponse;
import com.aztgg.api.auth.application.dto.request.UserUpdateRequest;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PutMapping("/{userId}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(UserResponse.from(updatedUser));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
