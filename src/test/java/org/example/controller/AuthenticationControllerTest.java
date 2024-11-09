package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.security.CustomUserDetailsService;
import org.example.config.security.JWTService;
import org.example.enums.UserTypeEnum;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.example.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import(SecurityTestConfig.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticate_ShouldReturnOk() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user", "password");
        LoginResponse loginResponse = new LoginResponse("mockToken", 3600L);

        Mockito.when(authenticationService.authenticate(Mockito.any(LoginRequest.class)))
                .thenReturn(loginResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("mockToken"))
                .andExpect(jsonPath("$.expiresIn").value(3600));
    }

    @Test
    void register_ShouldReturnOk() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username1");
        registerRequest.setPassword("password123");
        registerRequest.setUserType(UserTypeEnum.REGULAR);

        RegisterResponse registerResponse = new RegisterResponse("username1");

        Mockito.when(authenticationService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(registerResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username1"));
    }
}