package com.kusion.vote.admin.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kusion.vote.application.models.Config;
import com.kusion.vote.common.configs.Constants;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Config admin = (Config) session.getAttribute(Constants.SESSION_CURRENT_ADMIN_KEY);
        if(admin == null) {
            response.sendRedirect("/admin/login");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

}