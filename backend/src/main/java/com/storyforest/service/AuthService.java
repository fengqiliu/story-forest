package com.storyforest.service;

import com.storyforest.dto.AuthResponse;
import com.storyforest.dto.LoginRequest;
import com.storyforest.dto.RegisterRequest;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 认证响应（包含 Token 和用户信息）
     */
    AuthResponse login(LoginRequest request);

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 认证响应（包含 Token 和用户信息）
     */
    AuthResponse register(RegisterRequest request);

    /**
     * 刷新 Token
     *
     * @param refreshToken 刷新 Token
     * @return 新的认证响应
     */
    AuthResponse refreshToken(String refreshToken);

    /**
     * 退出登录
     *
     * @param userId 用户 ID
     */
    void logout(Long userId);

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 是否发送成功
     */
    boolean sendVerificationCode(String phone);

    /**
     * 验证验证码
     *
     * @param phone 手机号
     * @param code 验证码
     * @return 是否验证成功
     */
    boolean verifyVerificationCode(String phone, String code);
}
