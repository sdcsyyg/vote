package com.kusion.vote.admin.controllers;

import com.kusion.vote.application.models.Config;
import com.kusion.vote.common.configs.Constants;
import com.kusion.vote.common.controllers.AccessController;

public class AdminBaseController extends AccessController {

    protected static final String ADMIN_PRE = "/admin";

    protected static final String REDIRECT_TO_ADMIN_HOME = "redirect:/admin/index";
    protected static final String REDIRECT_TO_ADMIN_LOGIN = "redirect:/admin/login";

    /**
     * 返回 admin pc版
     * @param uri 必须以/开头
     * @return
     */
    protected String pc(String uri) {
        return ADMIN_PRE + uri;
    }

    protected static String pc(String... url){
        StringBuffer sb = new StringBuffer();
        sb.append(ADMIN_PRE).append("/");
        for(String str:url){
            sb.append(str).append("/");
        }
        return sb.substring(0, sb.length()-1);
    }

    protected Config getAdmin() {
        return (Config) session().getAttribute(Constants.SESSION_CURRENT_ADMIN_KEY);
    }

}
