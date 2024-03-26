package com.myim.common.util;


import com.myim.common.responseprotocal.BusinessStatus;
import com.myim.common.responseprotocal.ResultResponse;

/**
 * @author zYh
 */
public final class ResultResponseUtil {

    private ResultResponseUtil() {
    }

    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setResult(data);
        return response;
    }

    public static <T> ResultResponse<T> fail(int code, String message) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>();
    }


    public static <T> ResultResponse<T> fail(BusinessStatus businessStatus) {
        return fail(businessStatus.getValue(), businessStatus.getReasonPhrase());
    }

    public static <T> ResultResponse<T> successWithMsg(BusinessStatus businessStatus){
        ResultResponse<T> response = new ResultResponse<>();
        response.setMessage(businessStatus.getReasonPhrase());
        return response;
    }

}
