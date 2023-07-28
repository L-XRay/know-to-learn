package com.cqupt.knowtolearn.exception.assertion;

import cn.hutool.core.util.ArrayUtil;
import com.cqupt.knowtolearn.exception.ArgumentException;
import com.cqupt.knowtolearn.exception.BaseException;
import com.cqupt.knowtolearn.exception.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * @author Ray
 * @date 2023/7/28 17:13
 * @description
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = this.getMessage();
        if (ArrayUtil.isNotEmpty(args)) {
            msg = MessageFormat.format(this.getMessage(), args);
        }

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = this.getMessage();
        if (ArrayUtil.isNotEmpty(args)) {
            msg = MessageFormat.format(this.getMessage(), args);
        }

        return new ArgumentException(this, args, msg, t);
    }

}
