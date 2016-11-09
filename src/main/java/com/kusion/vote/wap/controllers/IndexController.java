package com.kusion.vote.wap.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.repos.VoteRepo;
import com.kusion.vote.common.controllers.AccessController;
import com.kusion.vote.common.enums.Status;

@Controller
@RequestMapping("/wap/")
public class IndexController extends AccessController {

    @Autowired
    VoteRepo voteRepo;

    @RequestMapping("index")
    public String index() {
        List<Vote> votes = voteRepo.findByStatus(Status.ACTIVE);
        request().setAttribute("votes", votes);
        return "/wap/index";
    }

}
