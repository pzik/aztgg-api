package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.request.UserUpdateRequest;
import com.aztgg.api.auth.domain.Role;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserRepository;
import com.aztgg.api.auth.domain.exception.AuthException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> AuthException.userNotFound(userId));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> AuthException.userNotFound(username));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public User createUser(String username, String password, String email, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw AuthException.usernameAlreadyExists();
        }
        if (userRepository.existsByEmail(email)) {
            throw AuthException.emailAlreadyExists();
        }

        User user = User.createNew(
            username,
            passwordEncoder.encode(password),
            email,
            role
        );
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = findById(userId);

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            // 이메일 중복 체크
            boolean isDuplicate = userRepository.existsByEmail(request.getEmail());
            if (isDuplicate) {
                throw AuthException.emailAlreadyExists();
            }
            user.changeEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.changePassword(encodedPassword);
        }

        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw AuthException.userNotFound(userId);
        }
        userRepository.deleteById(userId);
    }
}
