package com.kusion.vote.ext.wx.util;

import com.kusion.vote.common.configs.GlobalConfig;

public class WxConstants {

    /** 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节  **/
    public static final String OAUTH2_STATE = "xfny";

    public static final String LANG_CN = "zh_CN";
    public static final String LANG_TW = "zh_TW";
    public static final String LANG_EN = "en";

    public static final String OAUTH2_NOTIFY_URL = GlobalConfig.getProperty("wx.oauth.notify_url");
    public static final String PAY_NOTIFY_URL = GlobalConfig.getProperty("wx.pay.notify_url");

    public static final String PAY_TRADE_TYPE_JSAPI = "JSAPI";
    public static final String PAY_TRADE_TYPE_NATIVE = "NATIVE";
    public static final String PAY_TRADE_TYPE_APP = "APP";
    public static final String PAY_TRADE_TYPE_WAP = "WAP";
}
