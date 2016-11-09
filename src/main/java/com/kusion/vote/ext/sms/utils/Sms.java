package com.kusion.vote.ext.sms.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.kusion.vote.common.configs.GlobalConfig;
import com.kusion.vote.common.utils.DateUtil;
import com.kusion.vote.ext.sms.tpls.Callback;
import com.kusion.vote.ext.sms.tpls.RandcodeTpl;

/**
 * http://docs.ucpaas.com/doku.php?id=%E6%A8%A1%E6%9D%BF%E7%9F%AD%E4%BF%A1
 * 
 * @author ReedMi
 *
 */
public class Sms {

    private static Logger logger = Logger.getLogger(Sms.class);

    private static final String ACCOUNT_SID = GlobalConfig.getProperty("sms.ucpaas.sId");
    private static final String AUTH_TOKEN = GlobalConfig.getProperty("sms.ucpaas.authToken");
    private static final String VERSION = GlobalConfig.getProperty("sms.ucpaas.version");
    private static final String SERVER = GlobalConfig.getProperty("sms.ucpaas.server");

    private static final String APP_ID = GlobalConfig.getProperty("sms.ucpaas.appId");

    private static final String TPL_RANDCODE = "30235";// 短信模板ID

    public static Callback sendRandcode(String to, String param) {
        return sendTemplate(APP_ID, TPL_RANDCODE, to, param);
    }

    @SuppressWarnings("deprecation")
    public static Callback sendTemplate(String appId, String templateId, String to, String param) {

        Callback result = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            String timestamp = DateUtil.getCurrentLongDate();// 获取时间戳
            String signature = getSignature(ACCOUNT_SID, AUTH_TOKEN, timestamp);

            // 构造请求地址
            StringBuffer sb = new StringBuffer(SERVER);
            String url = sb.append("/").append(VERSION)
                    .append("/Accounts/").append(ACCOUNT_SID)
                    .append("/Messages/templateSMS")
                    .append("?sig=").append(signature).toString();

            RandcodeTpl randcodeTpl = new RandcodeTpl();
            randcodeTpl.setAppId(appId);
            randcodeTpl.setTemplateId(templateId);
            randcodeTpl.setTo(to);
            randcodeTpl.setParam(param);

            String body = JSONObject.toJSONString(randcodeTpl);
            body = "{\"templateSMS\":" + body + "}";

            HttpResponse response = HttpInvoker.post("application/json", ACCOUNT_SID, AUTH_TOKEN, timestamp, url, httpclient, body);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resp = EntityUtils.toString(entity, "UTF-8");

                logger.info("短信发送结果：" + resp);

                result = JSONResolver.build(resp);
            }
            // 确保HTTP响应内容全部被读出或者内容流被关闭
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static String getSignature(String accountSid, String authToken, String timestamp) throws Exception {
        String sig = accountSid + authToken + timestamp;
        String signature = EncryptUtil.md5Digest(sig);
        return signature;
    }

    public static void main(String[] args) {
        sendRandcode("15809691482", "12345678");
    }
}
