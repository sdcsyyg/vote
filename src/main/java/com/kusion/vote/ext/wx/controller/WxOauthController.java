package com.kusion.vote.ext.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.kusion.vote.application.models.User;
import com.kusion.vote.application.repos.UserRepo;
import com.kusion.vote.common.configs.Constants;
import com.kusion.vote.ext.wx.util.WxConstants;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ReedMi on 2016/9/3.
 *
 * 网页授权获取用户信息
 */
@Controller
@RequestMapping("/wx/oauth")
public class WxOauthController extends WxBaseController {

    @Autowired
    UserRepo userRepo;

    @RequestMapping("/login")
    public String login(@RequestParam(defaultValue = "base", required = false) String scope) {
        if(scope.equals("base")) {
            scope = WxConsts.OAUTH2_SCOPE_BASE;
        } else {
            scope = WxConsts.OAUTH2_SCOPE_USER_INFO;
        }
        String url = getBasePath() + WxConstants.OAUTH2_NOTIFY_URL;
        String oauthUrl = wxMpService.oauth2buildAuthorizationUrl(url, scope, WxConstants.OAUTH2_STATE);
        return "redirect:" + oauthUrl;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public String callback(@RequestParam String code, @RequestParam String state) throws WxErrorException {

        log.info("接收到微信回调 :code=" + code + "&state=" + state);

        if(StringUtils.isEmpty(code)) {
            request().setAttribute("err_msg", "微信登录失败：code为空");
        }
        if(!WxConstants.OAUTH2_STATE.equals(state)) {
            request().setAttribute("err_msg", "微信登录失败：state不匹配");
        }
        WxMpOAuth2AccessToken oauthToken = wxMpService.oauth2getAccessToken(code);
        System.out.println(JSONObject.toJSONString("============oauthToken:" + oauthToken));
        String scope = oauthToken.getScope();
        String openid = oauthToken.getOpenId();

        WxMpUser wxUser = null;
        if(scope.equals(WxConsts.OAUTH2_SCOPE_USER_INFO)) {
            wxUser = wxMpService.oauth2getUserInfo(oauthToken, WxConstants.LANG_CN);
        }

        // 自己的业务逻辑处理
        List<User> users = userRepo.findByOpenid(openid);
        User user = null;
        if(!users.isEmpty()) {
            user = users.get(0);
        } else {
            user = new User();
            user.setOpenid(openid);
            if(wxUser != null) {
                user.setNickname(wxUser.getNickname());
                user.setHeadimgurl(wxUser.getHeadImgUrl());
                user.setSex(wxUser.getSex());
                user.setCountry(wxUser.getCountry());
                user.setProvince(wxUser.getProvince());
                user.setCity(wxUser.getCity());
            }
            userRepo.save(user);
        }
        session().setAttribute(Constants.SESSION_CURRENT_USER_KEY, user);
        return "redirect:" + session().getAttribute(Constants.SESSION_WX_REDIRECT_URL);
    }
}
