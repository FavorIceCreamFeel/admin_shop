package com.smxr.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author smxr
 * @date 2019/12/5
 * @time 22:50
 * 手机验证码配置
 */
@Component
@ConfigurationProperties(prefix = "phonecode")
public class PhoneCode {
    private String AppID;//AppID
    private String account_SID;//开发者
    private String auth_token;//
    private String API_URl;//请求路径
    private String TemplateId;//短信模板id

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getAccount_SID() {
        return account_SID;
    }

    public void setAccount_SID(String account_SID) {
        this.account_SID = account_SID;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getAPI_URl() {
        return API_URl;
    }

    public void setAPI_URl(String API_URl) {
        this.API_URl = API_URl;
    }

    public String getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(String templateId) {
        TemplateId = templateId;
    }

    @Override
    public String toString() {
        return "PhoneCode{" +
                "AppID='" + AppID + '\'' +
                ", account_SID='" + account_SID + '\'' +
                ", auth_token='" + auth_token + '\'' +
                ", API_URl='" + API_URl + '\'' +
                ", TemplateId='" + TemplateId + '\'' +
                '}';
    }
}
