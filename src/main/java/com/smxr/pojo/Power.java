package com.smxr.pojo;



/**
 * @author smxr
 * @date 2020/1/15
 * @time 14:22
 */

public class Power {
    private int powerId;//权限id
    private String powerName;//权限名字
    private String powerDescribe;//权限描述
    private String powerSign;//权限字段

    public Power() {
    }

    public Power(int powerId, String powerName, String powerDescribe, String powerSign) {
        this.powerId = powerId;
        this.powerName = powerName;
        this.powerDescribe = powerDescribe;
        this.powerSign = powerSign;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerDescribe() {
        return powerDescribe;
    }

    public void setPowerDescribe(String powerDescribe) {
        this.powerDescribe = powerDescribe;
    }

    public String getPowerSign() {
        return powerSign;
    }

    public void setPowerSign(String powerSign) {
        this.powerSign = powerSign;
    }

    @Override
    public String toString() {
        return "Power{" +
                "powerId=" + powerId +
                ", powerName='" + powerName + '\'' +
                ", powerDescribe='" + powerDescribe + '\'' +
                ", powerSign='" + powerSign + '\'' +
                '}';
    }
}
