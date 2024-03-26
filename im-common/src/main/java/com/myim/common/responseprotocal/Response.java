package com.myim.common.responseprotocal;

/**
 * @author zYh
 */
public class Response {

    private int code = BusinessStatus.SUCCESS.getValue();

    private String message = BusinessStatus.SUCCESS.getReasonPhrase();

    public Response() {
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCode(BusinessStatus businessStatus) {
        this.code = businessStatus.getValue();
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
