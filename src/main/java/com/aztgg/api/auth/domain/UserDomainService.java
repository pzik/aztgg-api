package com.aztgg.api.auth.domain;

import com.aztgg.api.auth.domain.exception.InvalidEmailDomainException;
import com.aztgg.api.auth.domain.exception.InvalidNicknameDomainException;
import com.aztgg.api.auth.domain.exception.InvalidUsernameDomainException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

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
            .orElseThrow(InvalidUsernameDomainException::new);
    }

    public List<User> findAllUsersByIds(Collection<Long> userIds) {
        return userRepository.findAllByIdIn(userIds);
    }

    @Transactional
    public User createUser(String username, String password, String email, String nickname, Role role) {
        if (existsByUsername(username)) {
            throw new InvalidUsernameDomainException();
        }
        User user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .email(email)
            .nickname(nickname)
            .role(role)
            .build();
        return userRepository.save(user);
    }
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public User updateNickname(User user, String newNickname) {
        if (!user.getNickname().equals(newNickname) && existsByNickname(newNickname)) {
            throw new InvalidNicknameDomainException();
        }
        user.changeNickname(newNickname);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new InvalidUsernameDomainException();
        }
        userRepository.deleteById(userId);
    }
}
