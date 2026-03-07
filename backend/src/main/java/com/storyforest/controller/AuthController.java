package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.dto.AuthResponse;
import com.storyforest.dto.LoginRequest;
import com.storyforest.dto.RefreshTokenRequest;
import com.storyforest.dto.RegisterRequest;
import com.storyforest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户登录、注册、Token 刷新等认证相关接口")
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "使用手机号和密码登录，返回 JWT Token 和用户信息")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("登录请求：{}", request.getPhone());
        try {
            AuthResponse response = authService.login(request);
            return ApiResponse.success("登录成功", response);
        } catch (Exception e) {
            log.error("登录失败：{}", e.getMessage());
            return ApiResponse.error(1001, e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "使用手机号、验证码和密码注册新用户，注册成功后自动登录")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("注册请求：{}", request.getPhone());
        try {
            AuthResponse response = authService.register(request);
            return ApiResponse.success("注册成功", response);
        } catch (Exception e) {
            log.error("注册失败：{}", e.getMessage());
            return ApiResponse.error(1002, e.getMessage());
        }
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新 Token", description = "使用刷新 Token 获取新的访问 Token")
    public ApiResponse<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("刷新 Token 请求");
        try {
            AuthResponse response = authService.refreshToken(request.getRefreshToken());
            return ApiResponse.success("Token 刷新成功", response);
        } catch (Exception e) {
            log.error("刷新 Token 失败：{}", e.getMessage());
            return ApiResponse.error(1003, e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "用户退出登录，清除认证状态")
    public ApiResponse<Void> logout(@RequestAttribute("userId") Long userId) {
        log.info("退出登录，用户 ID: {}", userId);
        try {
            authService.logout(userId);
            return ApiResponse.success("退出成功", null);
        } catch (Exception e) {
            log.error("退出失败：{}", e.getMessage());
            return ApiResponse.error(1004, e.getMessage());
        }
    }

    /**
     * 发送验证码
     */
    @PostMapping("/code")
    @Operation(summary = "发送验证码", description = "向指定手机号发送短信验证码")
    public ApiResponse<Boolean> sendCode(
            @Parameter(description = "手机号", example = "13800138001", required = true)
            @RequestParam String phone) {
        log.info("发送验证码请求：{}", phone);
        try {
            boolean result = authService.sendVerificationCode(phone);
            return ApiResponse.success("验证码发送成功", result);
        } catch (Exception e) {
            log.error("发送验证码失败：{}", e.getMessage());
            return ApiResponse.error(1005, e.getMessage());
        }
    }

    /**
     * 验证验证码
     */
    @PostMapping("/code/verify")
    @Operation(summary = "验证验证码", description = "验证短信验证码是否正确")
    public ApiResponse<Boolean> verifyCode(
            @Parameter(description = "手机号", example = "13800138001", required = true)
            @RequestParam String phone,
            @Parameter(description = "验证码", example = "123456", required = true)
            @RequestParam String code) {
        log.info("验证验证码请求：{}", phone);
        try {
            boolean result = authService.verifyVerificationCode(phone, code);
            if (result) {
                return ApiResponse.success("验证码正确", true);
            } else {
                return ApiResponse.error(1006, "验证码错误或已过期", false);
            }
        } catch (Exception e) {
            log.error("验证验证码失败：{}", e.getMessage());
            return ApiResponse.error(1006, e.getMessage(), false);
        }
    }
}
