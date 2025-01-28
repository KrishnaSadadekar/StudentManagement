package com.schoolofcoding.app.dto;

import com.schoolofcoding.app.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String name;
    private String password;
    private String email;
    private Role role;
}
