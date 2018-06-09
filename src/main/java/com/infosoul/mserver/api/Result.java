package com.infosoul.mserver.api;

/**
 * REST返回结果.
 */
public class Result<T> {
    /**
     * 状态码,长度固定为6位的字符串.
     */
    private String status;
    /**
     * 状态信息,错误描述.
     */
    private String message;
    /**
     * 数据.
     */
    private T content;

    private Result(String status, String message, T content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }

    private Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     */
    @Deprecated
    public static <T> Result<T> buildResult(Status status, T content) {
        return new Result<T>(status.getCode(), status.getReason(), content);
    }

    private Result(String message) {
        this.message = message;
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     */
    @Deprecated
    protected static <T> Result<T> buildResult(Status status) {
        return new Result<T>(status.getCode(), status.getReason());
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     *
     * @param content 业务层处理结果
     */
    @Deprecated
    public static <T> Result<T> buildSuccessResult() {
        return buildResult(Status.OK);
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     *
     * @param content 业务层处理结果
     */
    @Deprecated
    public static <T> Result<T> buildSuccessResult(T content) {
        return buildResult(Status.OK, content);
    }

    @Deprecated
    public static <T> Result<T> buildErrorResult() {
        return buildResult(Status.ERROR);
    }

    /**
     * 成功后不含有message:OK
     *
     * @param <T>
     * @param content
     * @return
     */
    @Deprecated
    public static <T> Result<T> buildSuccessOkResult(String content) {
        return new Result<T>(Status.OK.getCode(), content);
    }

    /**
     * 创建一个带有<b>错误状态</b>的结果对象.
     *
     * @param status 错误状态
     */
    @Deprecated
    public static <T> Result<T> buildErrorResult(Status status) {
        return buildResult(status);
    }

    /**
     * 创建一个带有<b>错误状态</b>的结果对象.
     *
     * @param status  错误状态
     * @param message 错误信息
     */
    @Deprecated
    public static <T> Result<T> buildErrorResult(Status status, String message) {
        return new Result<T>(status.getCode(), message);
    }

    @Deprecated
    public static <T> Result<T> buildErrorResult(String message) {
        return new Result<T>(message);
    }
    // -- getters --//

    public String getMessage() {
        return message;
    }

    public T getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public enum Status {
        /**
         * 错误状态
         */
        ERROR("100", "ERROR"),

        /**
         * 成功状态,创建成功结果的时候自动设置.
         */
        OK("200", "OK"),

        /**
         * 错误的请求,参数不正确,如果没有更精确的状态表示,使用此状态.
         */
        BAD_REQUEST("400", "Bad Request"),

        /**
         * 未授权,未登录或登录失效.
         */
        UNAUTHORIZED("401", "Unauthorized"),

        /**
         * 请求方法错误
         */
        METHOD_NOT_ALLOWED("405", "Method Not Allowed"),

        /**
         * 服务器内部错误,如果没有更精确的状态表示,使用此状态.
         */
        INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

        UNKOWN_ERROR("600", "未知错误"),

        NOT_EXIST_ERROR("700", "数据不存在"),

        EXIST_ERROR("800", "数据已经存在");

        /**
         * 状态码,长度固定为6位的字符串.
         */
        private String code;
        /**
         * 错误信息.
         */
        private String reason;

        Status(final String code, final String reason) {
            this.code = code;
            this.reason = reason;
        }

        public String getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return code + ": " + reason;
        }
    }

}
