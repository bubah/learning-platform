package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "USERS", schema = "LEARNING_PLATFORM")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@Column(name="id")
	private UUID id = UUID.randomUUID();;
	
	@Column(nullable=false, unique =true, name="username")
	private String username;
	
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

	public User() {}

	public User(String username, String email, Role role) {
		this.username = username;
		this.email = email;
		this.role = role;
	}
}
