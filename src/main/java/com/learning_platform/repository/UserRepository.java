package com.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning_platform.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
