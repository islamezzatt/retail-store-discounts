package org.example.service.impl;

import org.example.config.security.JWTService;
import org.example.entity.User;
import org.example.exception.UsernameAlreadyExistsException;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.example.repository.UserRepository;
import org.example.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(JWTService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String jwt = jwtService.generateToken(loginRequest.getUsername());
        return new LoginResponse(jwt, jwtService.getExpirationTime());
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists."); // You can throw a custom exception instead
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserType(registerRequest.getUserType());
        user.setUserCreationDate(LocalDate.now());
        userRepository.save(user);
        return new RegisterResponse(user.getUsername());
    }

}
