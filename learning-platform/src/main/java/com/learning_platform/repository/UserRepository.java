package com.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning_platform.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
