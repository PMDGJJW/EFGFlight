package com.pmdgjjw.entity;

import jdk.net.SocketFlow;

import java.io.Serializable;

/**
 * @auth jian j w
 * @date 2020/5/9 14:54
 * @Description
 */
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Result implements Serializable {

    // 是否成功
    private Boolean flag;

    //响应状态码
    private Integer code;

    //响应消息内容
    private String message;

    //响应数据
    private Object data;

    public Result() {
    }

    public Result(boolean flag, SocketFlow.Status ok, String message) {
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
