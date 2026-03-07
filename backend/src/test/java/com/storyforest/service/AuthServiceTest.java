package com.storyforest.service;

import com.storyforest.dto.LoginRequest;
import com.storyforest.dto.AuthResponse;
import com.storyforest.entity.User;
import com.storyforest.repository.UserRepository;
import com.storyforest.service.impl.AuthServiceImpl;
import com.storyforest.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 认证服务单元测试
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testLoginSuccess() {
        // Given
        String phone = "13800138001";
        String password = "123456";
        String encodedPassword = "$2a$10$encodedPassword";
        Long userId = 1L;
        String ageGroup = "5-6 岁";
        String accessToken = "test-access-token";
        String refreshToken = "test-refresh-token";

        User user = User.builder()
                .id(userId)
                .phone(phone)
                .password(encodedPassword)
                .nickname("测试用户")
                .ageGroup(ageGroup)
                .isVip(false)
                .points(100)
                .status("ACTIVE")
                .build();

        LoginRequest request = LoginRequest.builder()
                .phone(phone)
                .password(password)
                .build();

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(userId, phone, ageGroup)).thenReturn(accessToken);
        when(jwtUtil.generateRefreshToken(userId, phone)).thenReturn(refreshToken);

        // When
        AuthResponse response = authService.login(request);

        // Then
        assertNotNull(response);
        assertEquals(accessToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
        assertNotNull(response.getUserInfo());
        assertEquals(userId, response.getUserInfo().getId());
        assertEquals(phone, response.getUserInfo().getPhone());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLoginUserNotFound() {
        // Given
        String phone = "13800138001";
        String password = "123456";

        LoginRequest request = LoginRequest.builder()
                .phone(phone)
                .password(password)
                .build();

        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testLoginWrongPassword() {
        // Given
        String phone = "13800138001";
        String password = "wrongpassword";
        String encodedPassword = "$2a$10$encodedPassword";

        User user = User.builder()
                .id(1L)
                .phone(phone)
                .password(encodedPassword)
                .status("ACTIVE")
                .build();

        LoginRequest request = LoginRequest.builder()
                .phone(phone)
                .password(password)
                .build();

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testSendVerificationCode() {
        // Given
        String phone = "13800138001";
        when(valueOperations.get(anyString())).thenReturn(null);

        // When
        boolean result = authService.sendVerificationCode(phone);

        // Then
        assertTrue(result);
        verify(valueOperations).set(anyString(), anyString(), eq(5L), eq(TimeUnit.MINUTES));
    }

    @Test
    void testVerifyVerificationCodeSuccess() {
        // Given
        String phone = "13800138001";
        String code = "123456";
        when(valueOperations.get("verify:code:" + phone)).thenReturn(code);

        // When
        boolean result = authService.verifyVerificationCode(phone, code);

        // Then
        assertTrue(result);
    }

    @Test
    void testVerifyVerificationCodeFailure() {
        // Given
        String phone = "13800138001";
        String code = "123456";
        String wrongCode = "654321";
        when(valueOperations.get("verify:code:" + phone)).thenReturn(code);

        // When
        boolean result = authService.verifyVerificationCode(phone, wrongCode);

        // Then
        assertFalse(result);
    }
}
