package com.example.blog.controller.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters")
    private String username;

    @NotBlank(message = "You must put some display name for your profile")
    @Size(min = 3, max = 24, message = "Display name must be between 3 and 24 characters")
    private String displayName;

    @NotBlank(message = "You have to specify your age")
    @Min(value = 13, message = "Dont use it child")
    @Max(value = 140, message = "At least put something realistic here")
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must have at least 8 symbols")
    private String password;

}
