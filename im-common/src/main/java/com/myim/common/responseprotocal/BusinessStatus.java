package com.myim.common.responseprotocal;

/**
 * @author zYh
 */
public enum BusinessStatus {

    /**
     * 服务器成功处理用户请求
     */
    SUCCESS(200, "Success"),

    /**
     * 请求参数不对
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * 用户无资源操作权限
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * 无接口方法访问权限
     */
    UNRESTMETHODAUTHORIZED(402, "Rest Method Unauthorized"),

    /**
     * 拒绝执行
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * 用户请求资源不存在
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * 无法获取样本数据
     */
    SAMPLE_DATA_NOT_FOUND(405, "Sample Data Not Found"),

    /**
     * 请求服务异常
     */
    SERVER_ERROR(500, "Server Error"),

    /**
     * 未设置相关数据
     */
    PRESET_DATA_ERROR(504, "Preset Data Error"),

    /**
     * 请求头未携带token
     */
    NO_TOKEN(600,"header not have token");

    private final int value;

    private final String reasonPhrase;

    BusinessStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
