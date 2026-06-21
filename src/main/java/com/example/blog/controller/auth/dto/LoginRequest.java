package com.example.blog.controller.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{validation.username.notblank}")
    @Size(min = 3, max = 24, message = "{validation.username.size}")
    private String username;

    @NotBlank(message = "{validation.password.notblank}")
    @Size(min = 8, message = "{validation.password.size}")
    private String password;
}