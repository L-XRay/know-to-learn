package com.cqupt.knowtolearn.exception.response;

import com.cqupt.knowtolearn.exception.enums.CommonResponseEnum;
import com.cqupt.knowtolearn.exception.enums.IResponseEnum;

/**
 * @author Ray
 * @date 2023/7/28 17:10
 * @description 基础返回结果
 */
public class BaseResponse {

    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
