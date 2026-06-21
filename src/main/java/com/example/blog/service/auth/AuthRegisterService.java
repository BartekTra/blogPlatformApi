package com.example.blog.service.auth;

import com.example.blog.controller.auth.dto.RegisterRequest;
import com.example.blog.controller.auth.dto.RegisterResponse;
import com.example.blog.controller.user.UserMapper;
import com.example.blog.exception.UserAlreadyExistsException;
import com.example.blog.model.User;
import com.example.blog.repository.user.UserRepository;
import com.example.blog.security.JwtProvider;
import com.example.blog.service.i18l.TranslationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final TranslationService translationService;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        validateUserDoesNotExist(request);
        return userMapper.toResponse(
                userRepository.save(
                        userMapper.toEntity(request)),
                jwtProvider.generateToken(request.getUsername()
                )
        );
    }

    private void validateUserDoesNotExist(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("error.auth.register.username_exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("error.auth.register.email_exists");
        }
    }
}
