package com.storyforest.exception;

/**
 * 禁止访问异常
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException() {
        super(403, "权限不足，无法访问该资源");
    }

    public ForbiddenException(String message) {
        super(403, message);
    }
}
