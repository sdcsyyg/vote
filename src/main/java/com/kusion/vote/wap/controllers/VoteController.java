package com.kusion.vote.wap.controllers;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.application.models.Competitor;
import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.models.VoteResult;
import com.kusion.vote.application.repos.CompetitorRepo;
import com.kusion.vote.application.repos.VoteRepo;
import com.kusion.vote.application.repos.VoteResultRepo;
import com.kusion.vote.common.controllers.AccessController;
import com.kusion.vote.common.enums.Status;
import com.kusion.vote.wap.forms.CompetitorForm;

@Controller
@RequestMapping("/wap/votes")
public class VoteController extends AccessController {

    @Autowired
    VoteRepo voteRepo;

    @Autowired
    CompetitorRepo competitorRepo;

    @Autowired
    VoteResultRepo voteResultRepo;

    /** 进入投票活动页面，列出手游选手并排名 **/
    @RequestMapping("vote/{voteId}")
    public String vote(@PathVariable Long voteId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        if(v == null) {
            return "/wap/votes/error";
        }
        List<Competitor> competitors = v.getCompetitors();
        DecimalFormat df = new DecimalFormat("######0.00");  
        for(Competitor c : competitors) {
            VoteResult vr = voteResultRepo.findByVoteAndCompetitor(v, c);
            if(vr != null) {
                c.setCheckInCount(vr.getCheckInCount());
                c.setCheckOutCount(vr.getCheckOutCount());
                c.setScoreCount(vr.getScoreCount());
                c.setVoteCount(vr.getVoteCount());
                if(vr.getVoteTimes() == 0) {
                    c.setAverage(0.0);
                } else if(vr.getScoreCount() == null || vr.getScoreCount() == 0) {
                    c.setAverage(0.0);
                } else {
                    c.setAverage(Double.valueOf(df.format(vr.getScoreCount() / vr.getVoteTimes())));
                }
            }
        }
        /** 冒泡排序 **/
        Competitor tc = null;
        for(int i = 0; i < (competitors.size()-1); i++) {
            for(int j = i+1; j < competitors.size(); j++) {
                switch (v.getVoteType()) {
                    case "CHECK":
                        if(competitors.get(i).getCheckInCount() < competitors.get(j).getCheckInCount()) {
                            tc = competitors.get(i);
                            competitors.set(i, competitors.get(j));
                            competitors.set(j, tc);
                        }
                        break;
                    case "VOTE":
                        if(competitors.get(i).getVoteCount() < competitors.get(j).getVoteCount()) {
                            tc = competitors.get(i);
                            competitors.set(i, competitors.get(j));
                            competitors.set(j, tc);
                        }
                        break;
                    case "SCORE":
                        if(competitors.get(i).getAverage() < competitors.get(j).getAverage()) {
                            tc = competitors.get(i);
                            competitors.set(i, competitors.get(j));
                            competitors.set(j, tc);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        request().setAttribute("vote", v);
        request().setAttribute("cmpetitors", competitors);
        return "/wap/votes/detail";
    }

    /** 进入报名页面 **/
    @RequestMapping("baoming/{voteId}")
    public String baoming(@PathVariable Long voteId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        if(v == null) {
            return "/wap/votes/error";
        }
        request().setAttribute("vote", v);
        return "/wap/votes/baoming";
    }

    /** 实现报名逻辑 **/
    @RequestMapping(value = "/baoming/{vid}", method = RequestMethod.POST)
    @ResponseBody
    public Object baoming(@PathVariable Long vid, CompetitorForm form) {
        Vote v = voteRepo.findByIdAndStatus(vid, Status.ACTIVE);
        if(v == null) {
            return "/wap/votes/error";
        }
        Competitor c = new Competitor();
        c.setName(form.getName());
        c.setSex(form.getSex());
        c.setPhone(form.getPhone());
        Competitor oc = competitorRepo.findByPhone(c.getPhone());
        if(oc == null) {
            c = competitorRepo.save(c);
        } else {
            c.setId(oc.getId());
        }

        if(v.getCompetitors().contains(c)) {
            return ok("您已经报过名了!");
        }

        v.addCompetitor(c);
        voteRepo.save(v);
        return ok("报名成功");
    }

    /** 进入投票页面 **/
    @RequestMapping("/vote/{voteId}/competitor/{competitorId}")
    public String votePage(@PathVariable Long voteId,@PathVariable Long competitorId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        if(v == null) {
            return "/wap/votes/error";
        }
        Competitor c = competitorRepo.findOne(competitorId);
        if(c == null) {
            return "/wap/votes/error";
        }
        DecimalFormat df = new DecimalFormat("######0.00");  
        VoteResult vr = voteResultRepo.findByVoteAndCompetitor(v, c);
        if(vr != null) {
            c.setCheckInCount(vr.getCheckInCount());
            c.setCheckOutCount(vr.getCheckOutCount());
            c.setVoteCount(vr.getVoteCount());
            c.setScoreCount(vr.getScoreCount());
            if(vr.getVoteTimes() == 0) {
                c.setAverage(0.00);
            }else if(vr.getScoreCount() == null || vr.getScoreCount() == 0){
                c.setAverage(0.00);
            } else {
                c.setAverage(Double.valueOf(df.format(vr.getScoreCount() / vr.getVoteTimes())));
            }
        }
        request().setAttribute("vote", v);
        request().setAttribute("competitor", c);
        return "/wap/votes/votePage";
    }

    /**
     * 赞成票
     */
    @RequestMapping(value = "/addIn/{voteId}/{competitorId}", method = RequestMethod.POST)
    @ResponseBody
    public Object addIn(@PathVariable Long voteId, @PathVariable Long competitorId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        Competitor c = competitorRepo.findOne(competitorId);
        VoteResult vr = testVote(v, c);
        if(vr == null) {
            return ok("表决失败");
        }
        // TODO ：枷锁，考虑并发
        vr.setCheckInCount(vr.getCheckInCount() + 1);
        voteResultRepo.save(vr);
        return ok("表决成功");
    }

    /**
     * 反对票
     */
    @RequestMapping(value = "/addOut/{voteId}/{competitorId}", method = RequestMethod.POST)
    @ResponseBody
    public Object addOut(@PathVariable Long voteId, @PathVariable Long competitorId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        Competitor c = competitorRepo.findOne(competitorId);
        VoteResult vr = testVote(v, c);
        if(vr == null) {
            return ok("表决失败");
        }
        // TODO ：枷锁，考虑并发
        vr.setCheckOutCount(vr.getCheckOutCount() + 1);
        voteResultRepo.save(vr);
        return ok("表决成功");
    }

    /**
     * 投票
     */
    @RequestMapping(value = "/addVote/{voteId}/{competitorId}", method = RequestMethod.POST)
    @ResponseBody
    public Object addVote(@PathVariable Long voteId, @PathVariable Long competitorId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        Competitor c = competitorRepo.findOne(competitorId);
        VoteResult vr = testVote(v, c);
        if(vr == null) {
            return ok("表决失败");
        }
        // TODO ：枷锁，考虑并发
        vr.setVoteCount(vr.getVoteCount() + 1);
        voteResultRepo.save(vr);
        return ok("表决成功");
    }

    /**
     * 打分票
     */
    @RequestMapping(value = "/addScore/{voteId}/{competitorId}/{score}", method = RequestMethod.POST)
    @ResponseBody
    public Object addScore(@PathVariable Long voteId, @PathVariable Long competitorId, @PathVariable String score) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        Competitor c = competitorRepo.findOne(competitorId);
        VoteResult vr = testVote(v, c);
        if(vr == null) {
            return ok("表决失败");
        }
        // TODO ：枷锁，考虑并发
        if(vr.getScoreCount() == null || vr.getScoreCount() == 0) {
            vr.setScoreCount(Double.valueOf(score));
        } else {
            vr.setScoreCount(vr.getScoreCount() + Double.valueOf(score));
        }
        vr.setVoteTimes(vr.getVoteTimes() + 1);
        voteResultRepo.save(vr);
        return ok("表决成功");
    }

    public VoteResult testVote(Vote v, Competitor c) {
        if(v == null || c == null) {
            return null;
        }
        // TODO: 对投票的人进行判断
        // TODO: 对投票的人进行判断
        // TODO: 对投票的人进行判断
        // TODO: 对投票的人进行判断
        VoteResult vr = voteResultRepo.findByVoteAndCompetitor(v, c);
        if(vr == null) {
            vr = new VoteResult();
            vr.setVote(v);
            vr.setCompetitor(c);
            vr = voteResultRepo.save(vr);
        }
        return vr;
    }

}
