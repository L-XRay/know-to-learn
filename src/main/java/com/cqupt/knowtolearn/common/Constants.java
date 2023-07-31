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

    /**
     * 课程状态：1编辑、2提审、3撤审、4通过、5拒绝、6发布
     */
    public enum CourseState {

        /** 1：编辑 */
        EDIT(1, "编辑"),
        /** 2：提审 */
        ARRAIGNMENT(2, "提审"),
        /** 3：撤审 */
        REVOKE(3, "撤审"),
        /** 4：通过 */
        PASS(4, "通过"),
        /** 5：拒绝 */
        REFUSE(5, "拒绝"),
        /** 6：发布 */
        PUBLISH(6, "发布");

        private Integer code;
        private String info;

        CourseState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 机构状态：0待审核、1通过、2拒绝
     */
    public enum OrgState {

        /** 0：待审核 */
        WAIT(0, "待审核"),
        /** 1：通过 */
        PASS(1, "通过"),
        /** 2：拒绝 */
        REFUSE(2, "拒绝");

        private Integer code;
        private String info;

        OrgState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 站内信状态：0未读、1已读
     */
    public enum StationMessageState {

        /** 0：未读 */
        NO_READ(0, "未读"),
        /** 1：已读 */
        READ(1, "已读");

        private Integer code;
        private String info;

        StationMessageState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
