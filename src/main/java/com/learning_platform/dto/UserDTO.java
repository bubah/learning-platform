package com.learning_platform.dto;

import com.learning_platform.model.Role;
import com.learning_platform.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.Optional;
import java.util.UUID;

public class UserDTO {
    private Optional<UUID> id = Optional.empty();;
    private Optional<String> username =  Optional.empty();;
    private Optional<String> email = Optional.empty();;
    private Optional<Role> role = Optional.empty();;


    public UserDTO(){}

    public UserDTO(User user){
        this.id = Optional.ofNullable(user.getId());
        this.username = Optional.ofNullable(user.getUsername());
        this.email = Optional.ofNullable(user.getEmail());
        this.role = Optional.ofNullable(user.getRole());
    }


    public UserDTO(Builder builder){
        this.id = builder.id;
        this.email = builder.email;
        this.username = builder.username;
        this.role = builder.role;
    }


    public UUID getId() {
        return this.id.orElse(null);
    }

    public void setId(UUID id) {
        this.id = Optional.ofNullable(id);
    }

    public String getUsername() {
        return this.username.orElse(null);
    }

    public void setUsername(String username) {
        this.username = Optional.ofNullable(username);
    }

    public String getEmail() {
        return this.email.orElse(null);
    }

    public void setEmail(String email) {
        this.email = Optional.ofNullable(email);
    }

    public Role getRole() {
        return this.role.orElse(null);
    }

    public void setRole(Role role) {
        this.role = Optional.ofNullable(role);
    }

    public static class Builder {
        private Optional<UUID> id = Optional.empty();;
        private Optional<String> username =  Optional.empty();;
        private Optional<String> email = Optional.empty();;
        private Optional<Role> role = Optional.empty();;

        public Builder setId(UUID id) {
            this.id = Optional.ofNullable(id);
            return this;
        }

        public Builder setUsername(String username) {
            this.username = Optional.ofNullable(username);
            return this;
        }

        public Builder setEmail(String email) {
            this.email = Optional.ofNullable(email);
            return this;
        }

        public Builder setRole(Role role) {
            this.role = Optional.ofNullable(role);
            return this;
        }

        public UserDTO build(){return new UserDTO(this);}
    }
}
