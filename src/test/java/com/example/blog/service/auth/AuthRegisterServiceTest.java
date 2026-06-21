package com.example.blog.service.auth;

import com.example.blog.controller.auth.dto.RegisterRequest;
import com.example.blog.controller.auth.dto.RegisterResponse;
import com.example.blog.controller.user.UserMapper;
import com.example.blog.exception.UserAlreadyExistsException;
import com.example.blog.model.User;
import com.example.blog.repository.user.UserRepository;
import com.example.blog.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthRegisterServiceTest {

    // should throw UserAlreadyExistsException when Username is already taken - todo
    // should throw UserAlreadyExistsException when Email is already taken - todo
    // should save user to db and return it with generated token to user as json

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthRegisterService authRegisterService;

    private RegisterRequest request;
    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setUsername("TestowyUzytkownik");
        request.setAge(16);
        request.setEmail("bartektrap@trapinsky.com");
        request.setDisplayName("DisplayTestName");
        request.setPassword("TestoweHaslo");
    }

    @Test
    void register_ShouldThrowUsernameException_WhenUsernameAlreadyExists() {
        // when && then
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authRegisterService.register(request)
        );

        assertEquals("error.auth.register.username_exists", exception.getMessage());
    }

    @Test
    void register_ShouldThrowEmailException_WhenEmailAlreadyExists() {
        // when && then
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authRegisterService.register(request)
        );

        assertEquals("error.auth.register.email_exists", exception.getMessage());
    }

    @Test
    void register_ShouldSaveUserAndReturnItWithToken_WhenRegistrationSuccessful() {
        User unmappedUser = new User();

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(request.getUsername());
        savedUser.setPassword(request.getPassword());
        savedUser.setEmail(request.getEmail());
        savedUser.setAge(request.getAge());
        savedUser.setDisplayName(request.getDisplayName());

        String mockToken = "super.tajny.token.jwt";
        RegisterResponse expectedResponse = new RegisterResponse(
                1L,
                request.getUsername(),
                request.getDisplayName(),
                request.getAge(),
                request.getEmail(),
                mockToken
                );

        when(userMapper.toEntity(request)).thenReturn(unmappedUser);

        when(userRepository.save(unmappedUser)).thenReturn(savedUser);

        when(jwtProvider.generateToken(request.getUsername())).thenReturn(mockToken);

        when(userMapper.toResponse(savedUser, mockToken)).thenReturn(expectedResponse);

        RegisterResponse actualResponse = authRegisterService.register(request);

        assertEquals(expectedResponse, actualResponse);

        verify(userRepository).save(unmappedUser);
    }
}