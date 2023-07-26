package com.cqupt.knowtolearn.service.captcha.impl;

import cn.hutool.core.net.NetUtil;
import com.cqupt.knowtolearn.service.captcha.IIdGenerator;
import com.cqupt.knowtolearn.service.captcha.SnowFlake;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Ray
 * @date 2022/10/12 20:41
 * @description 雪花算法生成随机ID
 */
@Component("snowFlakeGenerator")
public class SnowFlakeGenerator implements IIdGenerator {

    private SnowFlake snowFlake;

    @PostConstruct
    public void init() {
        // 0 ~ 31 位，可以采用配置的方式使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;

        snowFlake = new SnowFlake(dataCenterId,workerId);
    }

    @Override
    public synchronized String nextId(String prefix) {
        return prefix + snowFlake.nextId();
    }

}
