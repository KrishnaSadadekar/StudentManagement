package com.schoolofcoding.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private String username;
    private String role; // STUDENT, TRAINER, ADMIN
    private String token;

    public UserResponseDTO(String username, String role, String token) {
        this.username = username;
        this.role = role;
        this.token = token;
    }

}
