package com.kusion.vote.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.admin.forms.VoteForm;
import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.repos.VoteRepo;
import com.kusion.vote.common.enums.Status;

@Controller
@RequestMapping("/admin/votes")
public class VotesController extends AdminBaseController {

    @Autowired
    VoteRepo voteRepo;

    @RequestMapping("/{page}")
    public String render(@PathVariable String page) {
        return pc("/votes/" + page);
    }

    /** 对投票活动进行分页列表 **/
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public Page<Vote> page(@RequestParam String order, @RequestParam Integer limit, @RequestParam Integer offset) {
        Sort sort = new Sort(new Order(Direction.DESC, "id"));
        Pageable pageablea = new PageRequest(offset/limit, limit, sort);
        return voteRepo.findByStatus(pageablea, Status.ACTIVE);
    }

    /** 新增投票活动 **/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(VoteForm form) {
        Vote vote = new Vote();
        vote.setName(form.getName());
        vote.setScoreSystem(form.getScoreSystem());
        vote.setVoteType(form.getVoteType());
        vote.setStatus(Status.ACTIVE);
        voteRepo.save(vote);
        return ok("保存成功");
    }

    /** 调到跟新投票活动页面 **/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id) { 
        Vote vote = voteRepo.findOne(id);
        if(vote == null) {
            return pc("/votes/list");
        }
        request().setAttribute("vote", vote);
        return pc("/votes/edit");
    }

    /** 更新投票活动 **/
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, VoteForm form) {
        Vote vote = voteRepo.findOne(id);
        if(vote == null) {
            return failure("您要修改的信息不存在");
        }
        vote.setName(form.getName());
        vote.setScoreSystem(form.getScoreSystem());
        vote.setVoteType(form.getVoteType());
        voteRepo.save(vote);
        return ok("修改成功");
    }

    /** 删除投票活动 **/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        Vote vote = voteRepo.findOne(id);
        if(vote != null){
           vote.setStatus(Status.INACTIVE);
           voteRepo.save(vote);
        }
        return ok("删除成功");
    }

    /** 预览投票活动 **/
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id) {
        Vote vote = voteRepo.findOne(id);
        if(vote == null) {
            return pc("/votes/list");
        }
        request().setAttribute("vote", vote);
        return pc("/votes/view");
    }
}
