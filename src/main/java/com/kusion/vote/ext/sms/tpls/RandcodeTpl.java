package com.kusion.vote.ext.sms.tpls;

/**
 * 验证码模板短信
 * 
 * 验证码为{1}，感谢您的使用。
 * 
 * {@link http://docs.ucpaas.com/doku.php?id=%E6%A8%A1%E6%9D%BF%E7%9F%AD%E4%BF%A1}
 * 
 * @author ReedMi
 *
 */
public class RandcodeTpl {

    private String appId;
    private String templateId;
    private String to;
    private String param;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
