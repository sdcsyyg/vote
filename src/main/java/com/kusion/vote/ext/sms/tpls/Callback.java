package com.kusion.vote.ext.sms.tpls;

/**
 * 响应结果：
 * 
 * {
 *   "resp": { 
 *      "respCode":"000000", 
 *      "templateSMS": {
 *          "createDate":"20151023110204",
 *          "smsId":"99cbd209e40e59106f50b43e637898ec"
 *      }
 *   }
 * }
 * 
 * @author ReedMi
 *
 */
public class Callback {

    /**
     * 请求状态码，取值000000（成功）
     */
    private String respCode;

    /**
     * 短信标识符。一个由32个字符组成的短信唯一标识符 
     */
    private String smsId;

    /**
     * 短信的创建时间
     */
    private String createDate;

    /**
     * 表示短信验证码发送失败的条数。注：批量发送时，才会返回该字段
     */
    private String failure;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
