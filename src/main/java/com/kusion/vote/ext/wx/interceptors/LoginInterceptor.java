package com.kusion.vote.ext.wx.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
/**
    @Autowired
    UserRepo userRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        City city = (City) session.getAttribute(Constants.SESSION_CITY_KEY);
        if(city == null) {
            City _city = new City();
            _city.setName(Constants.DEFAULT_CITY_NAME);
            _city.setCode(Constants.DEFAULT_CITY_CODE);
            session.setAttribute(Constants.SESSION_CITY_KEY, _city);
        }
        User user = (User) session.getAttribute(Constants.SESSION_CURRENT_USER_KEY);
        if(user == null) {
            session.setAttribute(Constants.SESSION_CURRENT_USER_KEY, userRepo.findOne(1L));
        }
        return true;
    }
*/
}
