package com.infosoul.mserver.api;

import java.io.Serializable;

/**
 * RESTFull返回结果
 *
 * @author longxy
 * @date 2018-06-09 14:55
 */
public class ResponseRest implements Serializable {

    private static final long serialVersionUID = -6939262514788848052L;

    /**
     * 请求结果
     */
    private Boolean success;

    /**
     * 响应内容
     */
    private Object content;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 总记录数
     */
    private Long total;

    public enum Status {

        /**
         * 请求成功
         */
        OK("200", "OK"),

        /**
         * 错误的请求
         */
        BAD_REQUEST("400", "Bad Request"),

        /**
         * 未授权
         */
        UNAUTHORIZED("401", "Unauthorized"),

        /**
         * 定位资源不存在
         */
        NOT_FOUND("404", "not found"),

        /**
         * 请求方法错误
         */
        METHOD_NOT_ALLOWED("405", "Method Not Allowed"),

        /**
         * 服务器内部错误
         */
        INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

        /**
         * 未知错误
         */
        UNKNOWN_ERROR("600", "unknown"),

        /**
         * 数据不存在
         */
        NOT_EXIST("700", "data not exist"),

        /**
         * 数据已经存在
         */
        EXIST("800", "data is exist");

        private String code;

        private String msg;

        Status(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    private ResponseRest(Boolean success, Status status) {
        this.success = success;
        this.errorCode = status.getCode();
        this.errorMsg = status.getMsg();
    }

    private ResponseRest(Boolean success, Status status, String errorMsg) {
        this.success = success;
        this.errorCode = status.getCode();
        this.errorMsg = errorMsg;
    }

    private ResponseRest(Boolean success, Status status, Object content) {
        this.success = success;
        this.content = content;
        this.errorCode = status.getCode();
        this.errorMsg = status.getMsg();
    }

    private ResponseRest(Boolean success, Status status, Object content, Long total) {
        this.success = success;
        this.content = content;
        this.errorCode = status.getCode();
        this.errorMsg = status.getMsg();
        this.total = total;
    }

    public static ResponseRest success() {
        return new ResponseRest(true, Status.OK);
    }

    public static ResponseRest success(Object content) {
        return new ResponseRest(true, Status.OK, content);
    }

    public static ResponseRest success(Object content, Long total) {
        return new ResponseRest(true, Status.OK, content, total);
    }

    public static ResponseRest error(Status status) {
        return new ResponseRest(false, status);
    }

    public static ResponseRest error(Status status, String errorMsg) {
        return new ResponseRest(false, status, errorMsg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
