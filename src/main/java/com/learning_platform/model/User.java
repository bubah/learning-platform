package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.learning_platform.dto.UserDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private UUID id = UUID.randomUUID();
	
	@Column(nullable=false, unique =true, name="username")
	private String username;
	
	@Column(nullable=false, unique = true, name="email")
	private String email;
	
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name="last_login")
	private LocalDateTime lastLogin;

	@Column(name = "cognito_id", nullable = false)
	private UUID cognitoId;
	
	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public User() {}

	public User(UserDTO userDTO){
		Optional.ofNullable(userDTO.getEmail()).ifPresent((email) -> this.email = email);
		Optional.ofNullable(userDTO.getUsername()).ifPresent((username) -> this.username = username);
		Optional.ofNullable(userDTO.getRole()).ifPresent((role) -> this.role = role);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public UUID getCognitoId() {
		return cognitoId;
	}

	public void setCognitoId(UUID cognitoId) {
		this.cognitoId = cognitoId;
	}
}
