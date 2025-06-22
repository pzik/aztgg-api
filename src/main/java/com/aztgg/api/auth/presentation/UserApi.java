package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.dto.response.UserResponse;
import com.aztgg.api.auth.application.dto.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 API")
@RequestMapping("/v1/users")
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content),
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
    @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content)
})
public interface UserApi {

    @Operation(summary = "현재 사용자 정보 조회", description = """
            ## API 설명
            현재 로그인한 사용자의 정보를 조회합니다.
            """)
    @ApiResponse(
        responseCode = "200",
        description = "OK",
        content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @GetMapping("")
    ResponseEntity<UserResponse> getCurrentUser();

    @Operation(summary = "사용자 정보 수정 (관리자 전용)", description = """
            ## API 설명
            관리자가 특정 사용자의 정보를 수정합니다.
            """)
    @ApiResponse(
        responseCode = "200",
        description = "OK",
        content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @PutMapping("/{userId}")
    ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request);

    @Operation(summary = "사용자 삭제 (관리자 전용)", description = """
            ## API 설명
            관리자가 특정 사용자를 삭제합니다.
            """)
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId);
}
