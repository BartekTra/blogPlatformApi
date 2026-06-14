package com.example.blog.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String username;
    private String displayName;
    private Integer age;
    private String email;

    private String token;
}
