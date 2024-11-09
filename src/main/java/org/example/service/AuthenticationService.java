package org.example.service;

import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest loginRequest);

    RegisterResponse register(RegisterRequest registerRequest);
}
