package com.cqupt.knowtolearn.exception;

import com.cqupt.knowtolearn.exception.assertion.Assert;

/**
 * @author Ray
 * @date 2023/7/28 16:50
 * 只包装了 错误信息 的 {@link RuntimeException}.
 * 用于 {@link Assert} 中用于包装自定义异常信息
 */
public class WrapMessageException extends RuntimeException {

    public WrapMessageException(String message) {
        super(message);
    }

    public WrapMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
