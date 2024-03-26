package com.myim.common.responseprotocal;

/**
 * 结果反应
 *
 * @author zYh
 * @date 2023/01/03
 */
public class ResultResponse<T> extends Response {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}