package com.storyforest.exception;

/**
 * 未授权异常
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(401, "未授权访问，请先登录");
    }

    public UnauthorizedException(String message) {
        super(401, message);
    }
}
