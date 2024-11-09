package org.example.service.impl;


import org.example.config.security.JWTService;
import org.example.entity.User;
import org.example.enums.UserTypeEnum;
import org.example.exception.UsernameAlreadyExistsException;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void test_authenticate_success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        when(jwtService.generateToken(any())).thenReturn("mockedJwtToken");
        when(jwtService.getExpirationTime()).thenReturn(3600L);
        LoginResponse loginResponse = authenticationService.authenticate(loginRequest);

        assertNotNull(loginResponse);
        assertEquals("mockedJwtToken", loginResponse.getJwt());
        assertEquals(3600L, loginResponse.getExpiresIn());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void test_authenticate_usernameNotExists() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void test_register_success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username1");
        registerRequest.setPassword("password1");
        registerRequest.setUserType(UserTypeEnum.EMPLOYEE);
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        RegisterResponse registerResponse = authenticationService.register(registerRequest);

        assertNotNull(registerResponse);
        assertEquals("username1", registerResponse.getUsername());
        verify(userRepository).save(any());
    }

    @Test
    void test_register_usernameAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username1");
        registerRequest.setPassword("password1");
        registerRequest.setUserType(UserTypeEnum.EMPLOYEE);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        assertThrows(UsernameAlreadyExistsException.class, () -> authenticationService.register(registerRequest));
        verify(userRepository, times(0)).save(any());
    }
}
