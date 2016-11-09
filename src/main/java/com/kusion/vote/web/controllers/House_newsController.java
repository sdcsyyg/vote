package com.kusion.vote.web.controllers;

import com.kusion.vote.common.controllers.AccessController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/pc")
public class House_newsController extends AccessController {

    @RequestMapping("/loupanzixun")
    public String home() {
        return "/web/pc/loupanzixun";
    }

}
