package com.kusion.vote.admin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.admin.forms.LoginForm;
import com.kusion.vote.application.models.Config;
import com.kusion.vote.application.repos.ConfigRepo;
import com.kusion.vote.common.configs.Constants;

@Controller
@RequestMapping("/admin")
public class AccountsController extends AdminBaseController {

    @Autowired
    ConfigRepo configJpaRepo;

    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "admin/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(LoginForm loginForm) {

        Config admin = getAdmin();
        if(admin != null) {
            session().invalidate();//退出登录
        }

        String name = loginForm.getName();
        String password = loginForm.getPassword();

        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return failure("用户名和密码不能为空");
        }

        admin = configJpaRepo.findBySection(Constants.CONFIG_ADMIN_AUTH);
        if(!admin.getName().equals(name) || !admin.getValue().equals(password)) {
            return failure("用户名或密码错误");
        }

        session().setAttribute(Constants.SESSION_CURRENT_ADMIN_KEY, admin);
        session().setAttribute(Constants.SESSION_CURRENT_ADMIN_ID, admin.getId());
        return ok("登录成功");
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return REDIRECT_TO_ADMIN_LOGIN;
    }
}
