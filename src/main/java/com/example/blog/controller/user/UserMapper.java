package com.example.blog.controller.user;

import com.example.blog.controller.auth.dto.RegisterRequest;
import com.example.blog.controller.auth.dto.RegisterResponse;
import com.example.blog.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    public abstract User toEntity(RegisterRequest request);

    @AfterMapping
    protected void hashPassword(RegisterRequest request, @MappingTarget User user) {
        user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    public abstract RegisterResponse toResponse(User user, String token);
}
