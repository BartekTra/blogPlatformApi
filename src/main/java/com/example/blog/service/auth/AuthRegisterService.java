package com.example.blog.service.auth;

import com.example.blog.controller.auth.dto.RegisterRequest;
import com.example.blog.controller.auth.dto.RegisterResponse;
import com.example.blog.controller.user.UserMapper;
import com.example.blog.exception.UserAlreadyExistsException;
import com.example.blog.model.User;
import com.example.blog.repository.user.UserRepository;
import com.example.blog.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        validateUserDoesNotExist(request);

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userRepository.save(user), jwtProvider.generateToken(request.getUsername()));
    }

    private void validateUserDoesNotExist(RegisterRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Użytkownik już istnieje");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email jest już w użyciu");
        }
    }
}
