package com.smxr.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 14:09
 */
@ApiModel("消息返回体") //返回数据模型
public class ResponseBean implements Serializable {
    //返回数据模型属性
    @ApiModelProperty(name = "code码",dataType = "Int",value = "是否正确响应")
    private  int code;
    @ApiModelProperty(value = "消息的说明")
    private  String message;
    @ApiModelProperty(name = "返回数据",dataType = "Object",value = "返回的数据")
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
