package com.smxr.pojo;

import java.io.Serializable;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 14:09
 */
public class ResponseBean implements Serializable {
    private  int code;
    private  String message;
    private  Object data;

    public ResponseBean(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseBean() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
        return "ResponseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
