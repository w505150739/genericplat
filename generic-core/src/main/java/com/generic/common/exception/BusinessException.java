package com.generic.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuzhu
 * @description: 业务异常类
 * @date 2018/1/20 1:36
 */
@Setter
@Getter
public class BusinessException extends RuntimeException{
    private Object data;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data =data;
    }
}
