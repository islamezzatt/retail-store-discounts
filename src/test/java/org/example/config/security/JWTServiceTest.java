package org.example.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    private JWTService jwtService;
    private final String secretKey = "2213a4191c887c16f7ab81bc86ac81d9f3549e95819eca2e026e8854daf146d0";
    private final long jwtExpiration = 3600000L;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService(secretKey, jwtExpiration);
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractExpiration() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        Date expirationDate = jwtService.extractExpiration(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(username);
        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn("differentUser");

        String token = jwtService.generateToken(username);
        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }

}
