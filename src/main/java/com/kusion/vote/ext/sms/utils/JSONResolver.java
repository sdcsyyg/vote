package com.kusion.vote.ext.sms.utils;

import com.alibaba.fastjson.JSONObject;
import com.kusion.vote.ext.sms.tpls.Callback;

public class JSONResolver {

    public static Callback build(String data) {

        Callback callback = null;

        JSONObject json = JSONObject.parseObject(data);
        JSONObject resp = json.getJSONObject("resp");

        if(!resp.containsKey("respCode")) {
            return null;
        }
        callback = new Callback();
        String respCode = resp.getString("respCode");

        if(resp.containsKey("templateSMS")) {
            JSONObject templateSMS = resp.getJSONObject("templateSMS");
            String createDate = templateSMS.getString("createDate");
            String smsId = templateSMS.getString("smsId");
            callback.setCreateDate(createDate);
            callback.setSmsId(smsId);
        }

        callback.setRespCode(respCode);
        return callback;
    }

    public static void main(String[] args) {
        String data = "{\"resp\":{\"respCode\":\"000000\",\"templateSMS\":{\"createDate\":\"20151023153334\",\"smsId\":\"c128b673bd1a8234f20364969b45aa0c\"}}}";
        Callback c = build(data);
        System.out.println(JSONObject.toJSONString(c));
    }
}
