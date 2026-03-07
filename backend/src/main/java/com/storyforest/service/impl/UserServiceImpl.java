package com.storyforest.service;

import com.storyforest.dto.ChangePasswordRequest;
import com.storyforest.dto.UserResponse;
import com.storyforest.dto.UserUpdateRequest;
import com.storyforest.entity.PointsTransaction;
import com.storyforest.entity.User;
import com.storyforest.repository.PointsTransactionRepository;
import com.storyforest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PointsTransactionRepository pointsTransactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));
        return toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserInfo(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getAgeGroup() != null) {
            user.setAgeGroup(request.getAgeGroup());
        }

        userRepository.save(user);
        log.info("用户信息已更新，用户 ID: {}", userId);

        return toUserResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        // 验证原密码
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("用户密码已修改，用户 ID: {}", userId);
    }

    @Override
    @Transactional
    public UserResponse setAgeGroup(Long userId, String ageGroup) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        user.setAgeGroup(ageGroup);
        userRepository.save(user);
        log.info("用户年龄段已设置，用户 ID: {}, 年龄段：{}", userId, ageGroup);

        return toUserResponse(user);
    }

    @Override
    @Transactional
    public void addReadingTime(Long userId, int seconds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        int minutes = seconds / 60;
        user.setTotalReadingMinutes(user.getTotalReadingMinutes() + minutes);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addCompletedStory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        user.setCompletedStories(user.getCompletedStories() + 1);
        userRepository.save(user);
        log.info("用户完成故事数 +1，用户 ID: {}", userId);
    }

    @Override
    @Transactional
    public void addPoints(Long userId, int points, String type, String description, Long referenceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        // 更新用户积分
        int newBalance = user.getPoints() + points;
        user.setPoints(newBalance);
        userRepository.save(user);

        // 记录积分流水
        PointsTransaction transaction = PointsTransaction.builder()
                .userId(userId)
                .amount(points)
                .balance(newBalance)
                .type(type)
                .description(description)
                .referenceId(referenceId)
                .build();
        pointsTransactionRepository.save(transaction);

        log.info("用户积分变动：用户 ID={}, 积分={}, 类型={}, 描述={}", userId, points, type, description);
    }

    /**
     * 转换为用户响应 DTO
     */
    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .ageGroup(user.getAgeGroup())
                .isVip(user.getIsVip())
                .points(user.getPoints())
                .totalReadingMinutes(user.getTotalReadingMinutes())
                .completedStories(user.getCompletedStories())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
