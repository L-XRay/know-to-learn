package com.cqupt.knowtolearn.service.captcha;

/**
 * @author Ray
 * @date 2023/7/25 09:35
 * @description
 */
public interface IIdGenerator {
    String nextId(String prefix);
}
