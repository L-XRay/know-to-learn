package com.cqupt.knowtolearn.common;

/**
 * @author Ray
 * @date 2023/7/27 09:51
 * @description
 */
public class Constants {

    /**
     * 策略模式
     */
    public enum captchaMode {

        /**
         * 邮件
         */
        EMAIL(1, "邮箱验证码"),

        /**
         * 图像
         */
        PIC(2, "图像验证码");

        private Integer code;

        private String info;

        captchaMode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

}
