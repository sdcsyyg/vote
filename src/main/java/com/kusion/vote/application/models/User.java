package com.kusion.vote.application.models;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusion.vote.common.models.AbstractUser;

@Entity
public class User extends AbstractUser {

    private static final long serialVersionUID = -3146008836235567600L;

    private boolean disable = false;

    @JsonIgnore
    private String password;

    /***************jpush（极光推送）附加属性*********************/
    private String jpushRegisterId;

    private String jpushTag;

    private String jpushAlias;

    /*******************wx信息**********************************/
    private String openid;

    private String nickname;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private String unionid;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getJpushRegisterId() {
        return jpushRegisterId;
    }

    public void setJpushRegisterId(String jpushRegisterId) {
        this.jpushRegisterId = jpushRegisterId;
    }

    public String getJpushTag() {
        return jpushTag;
    }

    public void setJpushTag(String jpushTag) {
        this.jpushTag = jpushTag;
    }

    public String getJpushAlias() {
        return jpushAlias;
    }

    public void setJpushAlias(String jpushAlias) {
        this.jpushAlias = jpushAlias;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
