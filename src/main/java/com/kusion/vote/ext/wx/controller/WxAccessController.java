package com.kusion.vote.ext.wx.controller;

import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信公众号主服务接口，提供与微信服务器的信息交互
 * 服务器配置：域名+ /wx/access
 */
@Controller
@RequestMapping("/wx")
public class WxAccessController extends WxBaseController {

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/access")
    public void wechatCore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        log.info("接收到信息：signature:{} nonce:{} timestamp:{}", signature, nonce, timestamp);

        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            log.info("非法请求");
            return;
        }

        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            log.info("回显echostr");
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }

        String encryptType = request.getParameter("encrypt_type");
        if(StringUtils.isBlank(encryptType)) {
            encryptType = "raw";
        }

        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = this.coreService.route(inMessage);
            response.getWriter().write(outMessage.toXml());
            return;
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                        request.getInputStream(), this.configStorage, timestamp, nonce,
                        msgSignature);
            log.info("\n消息解密后内容为\n：{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.coreService.route(inMessage);
            response.getWriter()
                    .write(outMessage.toEncryptedXml(this.configStorage));
            return;
        }
        log.info("不可识别的加密类型");
        response.getWriter().println("不可识别的加密类型");
        return;
    }
}
