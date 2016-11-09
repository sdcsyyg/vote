package com.kusion.vote.ext.sms.utils;

import java.io.ByteArrayInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpInvoker {

    public static HttpResponse post(String cType, String accountSid, String authToken, String timestamp, String url, DefaultHttpClient httpclient,
            String body) throws Exception {

        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", cType);
        httppost.setHeader("Content-Type", cType + ";charset=utf-8");

        String src = accountSid + ":" + timestamp;
        String auth = EncryptUtil.base64Encoder(src);
        httppost.setHeader("Authorization", auth);

        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 查看返回值
        HttpResponse response = httpclient.execute(httppost);
        return response;
    }
}
