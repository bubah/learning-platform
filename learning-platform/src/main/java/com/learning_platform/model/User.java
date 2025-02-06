package com.learning_platform.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	@Id
	@GeneratedValue(generator = "uuid2")
	@Column(name="user_id")
	private long id;
	
	@Column(nullable=false, unique =true, name="username")
	private String username;
	
	@Column(nullable=false, name = "password_hash")
	private String password;
	
	@Column(nullable=false, unique = true, name="email")
	private String email;
	
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}
