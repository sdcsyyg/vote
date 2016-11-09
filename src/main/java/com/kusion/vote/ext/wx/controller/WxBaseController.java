package com.kusion.vote.ext.wx.controller;

import com.kusion.vote.common.controllers.AccessController;
import com.kusion.vote.ext.wx.service.CoreService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class WxBaseController extends AccessController {

    @Autowired
    protected WxMpConfigStorage configStorage;

    @Autowired
    protected WxMpService wxMpService;

    @Autowired
    protected CoreService coreService;

    public void passWxJsApiParams() throws WxErrorException {
        String url = request().getRequestURL().toString();
        WxJsapiSignature jsapi = wxMpService.createJsapiSignature(url);
        request().setAttribute("jsapi", jsapi);
    }
}