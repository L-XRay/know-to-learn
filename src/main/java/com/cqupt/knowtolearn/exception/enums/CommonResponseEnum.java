package com.cqupt.knowtolearn.exception.enums;

import com.cqupt.knowtolearn.exception.BaseException;
import com.cqupt.knowtolearn.exception.assertion.CommonExceptionAssert;
import com.cqupt.knowtolearn.exception.response.BaseResponse;

/**
 * @author Ray
 * @date 2023/7/28 17:16
 * @description
 */
public enum CommonResponseEnum implements CommonExceptionAssert {

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * 服务器繁忙，请稍后重试
     */
    SERVER_BUSY(501, "服务器繁忙"),
    /**
     * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    SERVER_ERROR(500, "网络异常"),

    /**
     * 5***，一般对应于{@link com.cqupt.knowtolearn.exception.ArgumentException}，系统封装的工具出现异常
     */

    // Time
    DATE_NOT_NULL(5001, "日期不能为空"),
    DATETIME_NOT_NULL(5001, "时间不能为空"),
    TIME_NOT_NULL(5001, "时间不能为空"),
    DATE_PATTERN_MISMATCH(5002, "日期[%s]与格式[%s]不匹配，无法解析"),
    PATTERN_NOT_NULL(5003, "日期格式不能为空"),
    PATTERN_INVALID(5003, "日期格式[%s]无法识别"),
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    CommonResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 校验返回结果是否成功
     * @param response 远程调用的响应
     */
    public static void assertSuccess(BaseResponse response) {
        SERVER_ERROR.assertNotNull(response);
        int code = response.getCode();
        if (CommonResponseEnum.SUCCESS.getCode() != code) {
            String msg = response.getMessage();
            throw new BaseException(code, msg);
        }
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
