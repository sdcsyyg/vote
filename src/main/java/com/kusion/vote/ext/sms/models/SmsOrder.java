package com.kusion.vote.ext.sms.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.kusion.vote.common.models.AbstractModel;

@Entity
@Table(name = "order_sms")
public class SmsOrder extends AbstractModel {

    private static final long serialVersionUID = 1L;

    private String respCode;

    private String smsId;

    private String createDate;

    private String content;

    private String phone;

    private String ip;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
