package com.lqh.wanandroid.network.entity;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
public class Response<T> {

    @JSONField(name = "status", alternateNames = {"errorCode"})
    private int status;
    @JSONField(name = "message", alternateNames = {"errorMsg"})
    private String message;
    @JSONField(name = "data", alternateNames = {"AppendData"})
    private T data;


    @JSONField(name = "status", alternateNames = {"errorCode"})
    public int getCode() {

        return status;
    }

    @JSONField(name = "message", alternateNames = {"errorMsg"})
    public String getMsg() {
        return message;
    }

    @JSONField(name = "data", alternateNames = {"AppendData"})
    public T getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.status = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.message = errorMsg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
