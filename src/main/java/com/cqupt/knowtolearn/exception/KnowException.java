package com.cqupt.knowtolearn.exception;

/**
 * @author Ray
 * @date 2023/7/20 16:35
 * @description 自定义异常
 */
public class KnowException extends RuntimeException {

    private Integer code;

    private String exception;

    public KnowException() {
    }

    public KnowException(Integer code, String message) {
        super(message);
        this.code = code;
        this.exception = message;
    }

    public KnowException(String message) {
        super(message);
        this.exception = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public static void capture(String message){
        throw new KnowException(message);
    }

}
