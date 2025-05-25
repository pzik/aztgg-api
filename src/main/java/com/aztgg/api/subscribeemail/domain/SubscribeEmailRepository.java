package com.aztgg.api.subscribeemail.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscribeEmailRepository extends JpaRepository<SubscribeEmail, Long> {

    Optional<SubscribeEmail> findByEmail(String email);
}
