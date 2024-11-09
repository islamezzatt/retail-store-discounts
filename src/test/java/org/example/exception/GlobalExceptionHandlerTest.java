package org.example.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.example.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleUsernameNotFoundException() {
        UsernameNotFoundException exception = new UsernameNotFoundException("Username not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUsernameNotFoundException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Username not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleUsernameAlreadyExistsException() {
        UsernameAlreadyExistsException exception = new UsernameAlreadyExistsException("Username already exists");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUsernameAlreadyExistsException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleExpiredJwtException() {
        ExpiredJwtException exception = new ExpiredJwtException(null, null, "JWT token expired");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("JWT Token has expired", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleMalformedJwtException() {
        MalformedJwtException exception = new MalformedJwtException("Malformed JWT token");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid JWT Token", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleSignatureException() {
        SignatureException exception = new SignatureException("Invalid JWT signature");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid JWT Signature", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("The username or password is incorrect", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleGeneralException() {
        Exception exception = new Exception("Some other error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
