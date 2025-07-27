package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.response.UserResponse;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;
import com.aztgg.api.auth.domain.UserRepository;
import com.aztgg.api.auth.domain.exception.AuthException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    public UserService(UserRepository userRepository,
		UserDomainService userDomainService) {
        this.userRepository = userRepository;
		this.userDomainService = userDomainService;
	}

    public UserResponse getCurrentUser(String username) {
        User user = userDomainService.findUserByUsername(username);
        return UserResponse.from(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw AuthException.userNotFound(userId);
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserResponse updateNickname(String username, String nickname) {
        User user = userDomainService.findUserByUsername(username);
        user = userDomainService.updateNickname(user, nickname);
        return UserResponse.from(user);
    }
}
