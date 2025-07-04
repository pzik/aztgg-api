package com.aztgg.api.auth.domain;

import com.aztgg.api.auth.domain.exception.AuthException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
public class UserDomainService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDomainService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> AuthException.userNotFound(username));
    }


    @Transactional
    public User createUser(String username, String password, String email, Role role) {
        if (existsByUsername(username)) {
            throw AuthException.usernameAlreadyExists();
        }
        if (existsByEmail(email)) {
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
    private Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
