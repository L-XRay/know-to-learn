package com.cqupt.knowtolearn.exception.enums;

/**
 * @author Ray
 * @date 2023/7/28 16:47
 * @description 异常返回码枚举接口
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMessage();
}
