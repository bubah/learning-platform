package com.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning_platform.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
