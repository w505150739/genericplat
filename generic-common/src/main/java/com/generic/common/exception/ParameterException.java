package com.generic.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liuyuzhu
 * @description: 参数异常捕获
 * @date 2018/1/20 1:35
 */
@Setter
@Getter
@ToString
public class ParameterException extends RuntimeException{

    private Object data;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(String message,Object data) {
        super(message);
        this.data = data;
    }
}
