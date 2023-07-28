package com.cqupt.knowtolearn.service.captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ray
 * @date 2023/7/26 18:24
 * @description
 */
public class SnowFlake {

    private Logger logger = LoggerFactory.getLogger(SnowFlake.class);

    /**
     * 数据中心(机房)ID
     */
    private long dataCenterId;
    /**
     * 机器ID
     */
    private long workerId;
    /**
     * 同一时间的序列号
     */
    private long sequence;

    /**
     * 开始时间戳:2020-05-20 08:00:00 +0800 CST
     */
    private final long twepoch = 1589923200000L;
    /**
     * 机房ID所占位数 5bit 最大:11111(二进制)-->31(十进制)
     */
    private final long dataCenterIdBits = 5L;
    /**
     * 机器ID所占位数 5bit 最大:11111(二进制)-->31(十进制)
     */
    private final long workerIdBits = 5L;
    /**
     * 同一时间的序列号所占位数 12bit 最大:111111111111(二进制)-->4095(十进制)
     */
    private final long sequenceBits = 12L;
    /**
     * 5 bit最多只能有31个数字，即机房id数量最多只能是32以内
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    /**
     * 5 bit最多只能有31个数字，即机器id数量最多只能是32以内
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 序列号掩码(0b111111111111==>0xfff==>4096)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 机器ID偏移量
     */
    private final long workerIdShift = sequenceBits;
    /**
     * 机房ID偏移量
     */
    private final long dataCenterIdShift = workerIdBits + sequenceBits;
    /**
     * 时间戳偏移量
     */
    private final long timestampLeftShift = dataCenterIdBits + workerIdBits + sequenceBits;
    /**
     * 最近一次时间戳
     */
    private long lastTimestamp = -1L;

    public SnowFlake(long dataCenterId, long workerId) {
        this(dataCenterId,workerId,0);
    }

    public SnowFlake(long dataCenterId, long workerId, long sequence) {
        if ( dataCenterId>maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("机房ID 大于最大值 %d 或者 小于 0",maxDataCenterId));
        }
        if ( workerId>maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("机器ID 大于最大值 %d 或者 小于 0",maxWorkerId));
        }
//        logger.info("机器启动中: 时间戳偏移量:{},机房ID占位:{},机器ID占位:{},序列号占位:{},机器ID:{}",
//                timestampLeftShift,dataCenterIdBits,workerIdBits,sequenceBits,workerId);
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.sequence = sequence;
    }

    /**
     * 获取机房ID
     * @return 机房ID
     */
    public long getDataCenterId() {
        return dataCenterId;
    }

    /**
     * 获取机器ID
     * @return 机器ID
     */
    public long getWorkerId() {
        return workerId;
    }

    /**
     * 获取最新的时间戳
     * @return 上一次生成ID的时间戳
     */
    public long getLastTimestamp() {
        return lastTimestamp;
    }

    /**
     * 获取下一个随机ID
     * @return 随机ID
     */
    public synchronized long nextId(){
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            logger.error("时间回退,最新时间戳:{}",lastTimestamp);
            throw new RuntimeException(String.format("时间回退,拒绝生成ID,实际相差: %d",lastTimestamp - currentTimestamp));
        }

        // 去重
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequenceMask+1) & sequenceMask;

            // 如果 sequence 序列号 大于 4095
            if(sequence == 0) {
                currentTimestamp = nextTimestamp(lastTimestamp);
            }
        } else {
            // 如果是当前时间第一次生成ID,就初始化为0
            sequence = 0;
        }

        // 记录最新时间戳
        lastTimestamp = currentTimestamp;

        // 偏移计算生成随机ID
        return ((currentTimestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long nextTimestamp(long lastTimestamp) {
        long currentTimestamp = System.currentTimeMillis();

        // 如果当前时间戳小于等于 序列号已经超过4095 那个时间戳
        // 继续获取最新时间戳
        if (currentTimestamp <= lastTimestamp) {
            currentTimestamp = System.currentTimeMillis();
        }

        return currentTimestamp;
    }
}
