package com.storyforest.exception;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceName) {
        super(404, resourceName + " 不存在");
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(404, resourceName + " 不存在：" + id);
    }
}
