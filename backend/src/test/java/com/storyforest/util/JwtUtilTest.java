package com.storyforest.util;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JWT 工具类单元测试
 */
class JwtUtilTest {

    private JwtUtil jwtUtil;

    private static final String TEST_SECRET = "test-secret-key-for-jwt-token-generation-and-validation";
    private static final Long TEST_EXPIRATION = 3600000L; // 1 小时
    private static final Long TEST_REFRESH_EXPIRATION = 86400000L; // 24 小时

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", TEST_EXPIRATION);
        ReflectionTestUtils.setField(jwtUtil, "refreshExpiration", TEST_REFRESH_EXPIRATION);
    }

    @Test
    void testGenerateToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String ageGroup = "5-6 岁";

        // When
        String token = jwtUtil.generateToken(userId, username, ageGroup);

        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGetUsernameFromToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String ageGroup = "5-6 岁";
        String token = jwtUtil.generateToken(userId, username, ageGroup);

        // When
        String extractedUsername = jwtUtil.getUsernameFromToken(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void testGetUserIdFromToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String ageGroup = "5-6 岁";
        String token = jwtUtil.generateToken(userId, username, ageGroup);

        // When
        Long extractedUserId = jwtUtil.getUserIdFromToken(token);

        // Then
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testGetAgeGroupFromToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String ageGroup = "5-6 岁";
        String token = jwtUtil.generateToken(userId, username, ageGroup);

        // When
        String extractedAgeGroup = jwtUtil.getAgeGroupFromToken(token);

        // Then
        assertEquals(ageGroup, extractedAgeGroup);
    }

    @Test
    void testValidateToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String ageGroup = "5-6 岁";
        String token = jwtUtil.generateToken(userId, username, ageGroup);

        // When
        Boolean isValid = jwtUtil.validateToken(token, username);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testValidateTokenWithWrongUsername() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String wrongUsername = "13800138002";
        String token = jwtUtil.generateToken(userId, username, "5-6 岁");

        // When
        Boolean isValid = jwtUtil.validateToken(token, wrongUsername);

        // Then
        assertFalse(isValid);
    }

    @Test
    void testGenerateRefreshToken() {
        // Given
        Long userId = 1L;
        String username = "13800138001";

        // When
        String refreshToken = jwtUtil.generateRefreshToken(userId, username);

        // Then
        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
    }

    @Test
    void testIsTokenExpired() {
        // Given
        Long userId = 1L;
        String username = "13800138001";
        String token = jwtUtil.generateToken(userId, username, "5-6 岁");

        // When
        Boolean isExpired = jwtUtil.isTokenExpired(token);

        // Then
        assertFalse(isExpired);
    }
}
