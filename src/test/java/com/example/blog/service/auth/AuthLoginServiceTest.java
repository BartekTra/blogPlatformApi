package com.example.blog.service.auth;

import com.example.blog.controller.auth.dto.LoginRequest;
import com.example.blog.controller.auth.dto.LoginResponse;
import com.example.blog.repository.user.UserRepository;
import com.example.blog.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthLoginServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthLoginService authLoginService;

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testUser");
        request.setPassword("correctPassword");

        String expectedToken = "fake-jwt-token-123";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        when(jwtProvider.generateToken("testUser")).thenReturn(expectedToken);

        LoginResponse response = authLoginService.login(request);

        assertNotNull(response, "Response nie powinien być nullem");
        assertEquals(expectedToken, response.getToken(), "Token w odpowiedzi musi zgadzać się z wygenerowanym");

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider, times(1)).generateToken("testUser");
    }

    @Test
    void login_ShouldThrowException_WhenCredentialsAreInvalid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Nieprawidłowy login lub hasło"));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authLoginService.login(request)
        );

        assertEquals("Nieprawidłowy login lub hasło", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider, never()).generateToken(anyString());
    }
}