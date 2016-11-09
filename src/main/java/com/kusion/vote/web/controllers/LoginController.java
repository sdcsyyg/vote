package com.kusion.vote.web.controllers;

import com.kusion.vote.common.controllers.AccessController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/pc")
public class LoginController extends AccessController {
    @RequestMapping("login")
    public String home() {
        return "/web/pc/login";
    }
}
