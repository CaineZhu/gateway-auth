package com.caine.platform.common;

import com.caine.platform.common.constant.Constant;

import java.io.Serializable;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 9:57 2019/9/6
 * @Modified By:
 */
public class ResultMap<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public ResultMap success(String msg) {
        return this.common(Constant.RESPONSE_SUCCESS, msg, null);
    }

    public ResultMap success(T data) {
        return this.common(Constant.RESPONSE_SUCCESS, "success", data);
    }

    public ResultMap failed(String msg) {
        return this.common(Constant.RESPONSE_FAILED, msg, null);
    }

    public ResultMap failed(T data) {
        return this.common(Constant.RESPONSE_FAILED, "failed", data);
    }

    public ResultMap error(String msg) {
        return this.common(Constant.RESPONSE_ERROR, msg, null);
    }

    public ResultMap error(T data) {
        return this.common(Constant.RESPONSE_ERROR, "error", data);
    }

    public ResultMap common(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
