package com.example.blog.controller.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "{validation.username.notblank}")
    @Size(min = 3, max = 24, message = "{validation.username.size}")
    private String username;

    @NotBlank(message = "{validation.displayname.notblank}")
    @Size(min = 3, max = 24, message = "{validation.displayname.size}")
    private String displayName;

    // Zmienione z @NotBlank na @NotNull dla typu Integer!
    @NotNull(message = "{validation.age.notnull}")
    @Min(value = 13, message = "{validation.age.min}")
    @Max(value = 140, message = "{validation.age.max}")
    private Integer age;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.password.notblank}")
    @Size(min = 8, message = "{validation.password.size}")
    private String password;
    
}
