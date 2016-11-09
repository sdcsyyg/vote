package com.kusion.vote.ext.jpush;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kusion.vote.common.configs.GlobalConfig;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Jpush {

    static Logger log = LoggerFactory.getLogger(Jpush.class);

    final static String JPUST_SECRET = GlobalConfig.getProperty("jpush.secret");
    final static String JPUST_APPKEY = GlobalConfig.getProperty("jpush.appkey");

    public static void toAll(String msg) {
        JPushClient jpushClient = new JPushClient(JPUST_SECRET, JPUST_APPKEY);
        PushPayload payload = PushPayload.alertAll(msg);
        try {
            PushResult result = jpushClient.sendPush(payload);
            if(result.isResultOK()) {
                log.info("推送成功：" + JSONObject.toJSONString(result));
            } else {
                log.error("推送失败：" + JSONObject.toJSONString(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送出现异常", e);
        }
    }

    /**
     * 发送指定msg给ids用户组
     * @param ids
     * @param msg
     */
    public static void toRegisterIds(List<String> ids, String msg) {
        JPushClient jpushClient = new JPushClient(JPUST_SECRET, JPUST_APPKEY);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(ids))
                .setNotification(Notification.alert(msg))
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            if(result.isResultOK()) {
                log.info("推送成功：" + JSONObject.toJSONString(result));
            } else {
                log.error("推送失败：" + JSONObject.toJSONString(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送出现异常", e);
        }
    }

}
