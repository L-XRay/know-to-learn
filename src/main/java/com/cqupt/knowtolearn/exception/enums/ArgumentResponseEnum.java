package com.cqupt.knowtolearn.exception.enums;

import com.cqupt.knowtolearn.exception.assertion.CommonExceptionAssert;

/**
 * @author Ray
 * @date 2023/7/28 17:27
 * @description
 */
public enum ArgumentResponseEnum implements CommonExceptionAssert {

    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(6000, "参数校验异常"),

    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    ArgumentResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
