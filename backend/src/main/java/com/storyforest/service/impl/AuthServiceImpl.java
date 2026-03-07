package com.storyforest.service.impl;

import com.storyforest.dto.AuthResponse;
import com.storyforest.dto.LoginRequest;
import com.storyforest.dto.RegisterRequest;
import com.storyforest.entity.User;
import com.storyforest.repository.UserRepository;
import com.storyforest.service.AuthService;
import com.storyforest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String VERIFICATION_CODE_PREFIX = "verify:code:";
    private static final String VERIFICATION_CODE_EXPIRE = "5"; // 5 分钟

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("用户登录，手机号：{}", request.getPhone());

        // 查找用户
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 检查账号状态
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }

        // 更新最后登录时间
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // 生成 Token
        String accessToken = jwtUtil.generateToken(user.getId(), user.getPhone(), user.getAgeGroup());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getPhone());

        // 构建响应
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(604800L) // 7 天
                .userInfo(toUserInfoDTO(user))
                .build();
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("用户注册，手机号：{}", request.getPhone());

        // 检查手机号是否已存在
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("手机号已被注册");
        }

        // 验证验证码
        if (!verifyVerificationCode(request.getPhone(), request.getCode())) {
            throw new RuntimeException("验证码错误或已过期");
        }

        // 创建用户
        User user = User.builder()
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname() != null ? request.getNickname() : generateDefaultNickname())
                .avatar("")
                .isVip(false)
                .points(100) // 注册赠送 100 积分
                .totalReadingMinutes(0)
                .completedStories(0)
                .status("ACTIVE")
                .build();

        userRepository.save(user);

        // 生成 Token
        String accessToken = jwtUtil.generateToken(user.getId(), user.getPhone(), user.getAgeGroup());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getPhone());

        // 删除已使用的验证码
        redisTemplate.delete(VERIFICATION_CODE_PREFIX + request.getPhone());

        // 构建响应
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(604800L)
                .userInfo(toUserInfoDTO(user))
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        log.info("刷新 Token");

        // 验证刷新 Token
        if (!jwtUtil.validateToken(refreshToken, jwtUtil.getUsernameFromToken(refreshToken))) {
            throw new RuntimeException("刷新 Token 无效或已过期");
        }

        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String ageGroup = jwtUtil.getAgeGroupFromToken(refreshToken);

        // 查找用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 检查账号状态
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }

        // 生成新的 Token
        String newAccessToken = jwtUtil.generateToken(userId, username, ageGroup);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId, username);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(604800L)
                .userInfo(toUserInfoDTO(user))
                .build();
    }

    @Override
    public void logout(Long userId) {
        log.info("用户退出登录，用户 ID: {}", userId);
        // 可以在这里将 Token 加入黑名单
        // 或者清除 Redis 中的相关缓存
    }

    @Override
    public boolean sendVerificationCode(String phone) {
        log.info("发送验证码，手机号：{}", phone);

        // 生成 6 位数字验证码
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 存储到 Redis，5 分钟过期
        String key = VERIFICATION_CODE_PREFIX + phone;
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        // TODO: 调用短信服务发送验证码
        log.info("验证码：{} (开发环境，请查看日志)", code);

        return true;
    }

    @Override
    public boolean verifyVerificationCode(String phone, String code) {
        String key = VERIFICATION_CODE_PREFIX + phone;
        String storedCode = redisTemplate.opsForValue().get(key);

        if (storedCode == null) {
            log.warn("验证码不存在或已过期，手机号：{}", phone);
            return false;
        }

        return storedCode.equals(code);
    }

    /**
     * 生成默认昵称
     */
    private String generateDefaultNickname() {
        String[] prefixes = {"快乐", "聪明", "可爱", "活泼", "善良", "勇敢", "好奇", "天真"};
        String[] suffixes = {"小书虫", "小故事王", "阅读家", "故事迷", "小博士", "小达人"};

        String prefix = prefixes[(int) (Math.random() * prefixes.length)];
        String suffix = suffixes[(int) (Math.random() * suffixes.length)];

        return prefix + suffix + (int) (Math.random() * 1000);
    }

    /**
     * 转换为用户信息 DTO
     */
    private AuthResponse.UserInfoDTO toUserInfoDTO(User user) {
        return AuthResponse.UserInfoDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .ageGroup(user.getAgeGroup())
                .isVip(user.getIsVip())
                .points(user.getPoints())
                .totalReadingMinutes(user.getTotalReadingMinutes())
                .completedStories(user.getCompletedStories())
                .build();
    }
}
