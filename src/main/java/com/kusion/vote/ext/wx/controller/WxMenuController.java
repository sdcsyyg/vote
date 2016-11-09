package com.kusion.vote.ext.wx.controller;

import com.kusion.vote.ext.wx.config.MainConfig;
import com.kusion.vote.ext.wx.util.WxConstants;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ReedMi on 2016/9/27.
 */
@Controller
@RequestMapping("/wx/menu")
public class WxMenuController extends WxBaseController {

    @RequestMapping("/list")
    @ResponseBody
    public Object getMenus() throws WxErrorException {
        WxMenu wxMenu = wxMpService.getMenuService().menuGet();
        return wxMenu;
    }

    @RequestMapping("/create")
    @ResponseBody
    public Object create() {

        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.BUTTON_VIEW);
        button1.setName("进入商城");
        button1.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://www.xinfengxianshu.com", "snsapi_base", "1"));

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("菜单测试");

        WxMenuButton button21 = new WxMenuButton();
        button21.setType(WxConsts.BUTTON_LOCATION_SELECT);
        button21.setName("地理位置选择");
        button21.setKey(WxConsts.BUTTON_LOCATION_SELECT);

        WxMenuButton button22 = new WxMenuButton();
        button22.setType(WxConsts.BUTTON_PIC_WEIXIN);
        button22.setName("微信相册发图");
        button22.setKey(WxConsts.BUTTON_PIC_WEIXIN);

        WxMenuButton button23 = new WxMenuButton();
        button23.setType(WxConsts.BUTTON_SCANCODE_PUSH);
        button23.setName("扫码推事件");
        button23.setKey(WxConsts.BUTTON_SCANCODE_PUSH);

        WxMenuButton button24 = new WxMenuButton();
        button24.setType(WxConsts.BUTTON_SCANCODE_WAITMSG);
        button24.setName("扫码推2");
        button24.setKey(WxConsts.BUTTON_SCANCODE_WAITMSG);

        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button23);
        button2.getSubButtons().add(button24);

        WxMenuButton button3 = new WxMenuButton();
        button3.setType(WxConsts.BUTTON_CLICK);
        button3.setName("使用帮助");
        button3.setKey("help");

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        try {
            wxMpService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return failure("创建失败");
        }

        return ok("创建成功");
    }

    public static void main(String[] args) {
        MainConfig mc = new MainConfig();
        WxMpService wxMpService = mc.wxMpService();

        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.BUTTON_VIEW);
        button1.setName("商城首页");
        button1.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://www.xinfengxianshu.com", WxConsts.OAUTH2_SCOPE_USER_INFO, WxConstants.OAUTH2_STATE));

        WxMenuButton button2 = new WxMenuButton();
        button2.setType(WxConsts.BUTTON_VIEW);
        button2.setName("分类选购");
        button2.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://www.xinfengxianshu.com/wap/ware/list", WxConsts.OAUTH2_SCOPE_USER_INFO, WxConstants.OAUTH2_STATE));

        WxMenuButton button3 = new WxMenuButton();
        button3.setType(WxConsts.BUTTON_VIEW);
        button3.setName("关于欣丰");
        button3.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://www.xinfengxianshu.com/wap/article/ab1de4317e814d13bb950121f71cb32e", WxConsts.OAUTH2_SCOPE_USER_INFO, WxConstants.OAUTH2_STATE));

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        try {
            wxMpService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
