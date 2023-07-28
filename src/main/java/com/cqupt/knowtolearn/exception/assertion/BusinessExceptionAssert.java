package com.cqupt.knowtolearn.exception.assertion;

import cn.hutool.core.util.ArrayUtil;
import com.cqupt.knowtolearn.exception.enums.IResponseEnum;
import com.cqupt.knowtolearn.exception.BaseException;
import com.cqupt.knowtolearn.exception.BusinessException;

import java.text.MessageFormat;

/**
 * @author Ray
 * @date 2023/7/28 16:57
 * @description 业务异常断言
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = this.getMessage();
        if (ArrayUtil.isNotEmpty(args)) {
            msg = MessageFormat.format(this.getMessage(), args);
        }

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = this.getMessage();
        if (ArrayUtil.isNotEmpty(args)) {
            msg = MessageFormat.format(this.getMessage(), args);
        }

        return new BusinessException(this, args, msg, t);
    }

}
