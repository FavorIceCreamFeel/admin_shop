package com.smxr.pojo;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 14:10
 */
public class ResponseUtils {
    /**
     * 响应成功
     */
    public static ResponseBean Success(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(1);
        responseBean.setMessage("请求成功！");
        responseBean.setData(null);
        return responseBean;
    }
    public static ResponseBean Success(String data){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(1);
        responseBean.setMessage("请求成功！");
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Success(String data, String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(1);
        responseBean.setMessage(msg);
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Success(int code, String data){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMessage("请求成功！");
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Success(int code, String data, String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMessage(msg);
        responseBean.setData(data);
        return responseBean;
    }
    /**
     * 响应失败
     */
    public static ResponseBean Error(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(0);
        responseBean.setMessage("请求失败！");
        responseBean.setData(null);
        return responseBean;
    }
    public static ResponseBean Error(String data){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(0);
        responseBean.setMessage("请求失败！");
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Error(String data, String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(0);
        responseBean.setMessage(msg);
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Error(int code, String data){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMessage("请求失败！");
        responseBean.setData(data);
        return responseBean;
    }
    public static ResponseBean Error(int code, String data, String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMessage(msg);
        responseBean.setData(data);
        return responseBean;
    }

}
