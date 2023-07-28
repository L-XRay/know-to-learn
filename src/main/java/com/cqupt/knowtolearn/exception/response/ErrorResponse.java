package com.cqupt.knowtolearn.exception.response;

/**
 * @author Ray
 * @date 2023/7/28 17:21
 * @description 错误返回结果
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}