package org.example.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.example.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        String errorMessage;
        HttpStatus httpStatus;
        errorResponse.setTimestamp(LocalDateTime.now());
        switch (ex) {
            case ExpiredJwtException expiredJwtException -> {
                errorMessage = "JWT Token has expired";
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            case MalformedJwtException malformedJwtException -> {
                errorMessage = "Invalid JWT Token";
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            case SignatureException signatureException -> {
                errorMessage = "Invalid JWT Signature";
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            case BadCredentialsException badCredentialsException -> {
                errorMessage = "The username or password is incorrect";
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            case null, default -> {
                errorMessage = "An unexpected error occurred";
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        errorResponse.setMessage(errorMessage);
        errorResponse.setCode(httpStatus.value());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
