package com.kusion.vote.wap.controllers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.application.models.Competitor;
import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.models.VoteRecord;
import com.kusion.vote.application.models.VoteResult;
import com.kusion.vote.application.repos.CompetitorRepo;
import com.kusion.vote.application.repos.VoteRecordRepo;
import com.kusion.vote.application.repos.VoteRepo;
import com.kusion.vote.application.repos.VoteResultRepo;
import com.kusion.vote.common.configs.Constants;
import com.kusion.vote.common.controllers.AccessController;
import com.kusion.vote.common.enums.Status;
import com.kusion.vote.wap.forms.CompetitorForm;
import com.kusion.vote.wap.forms.VotingForm;

@Controller
@RequestMapping("/wap/votes")
public class VoteController extends AccessController {

    @Autowired
    VoteRepo voteRepo;

    @Autowired
    CompetitorRepo competitorRepo;

    @Autowired
    VoteResultRepo voteResultRepo;

    @Autowired
    VoteRecordRepo voteRecordRepo;

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
                    case Constants.VOTE_TYPE_CHECK:
                        if(competitors.get(i).compareTo(competitors.get(j), Constants.VOTE_TYPE_CHECK) < 0) {
                            tc = competitors.get(i);
                            competitors.set(i, competitors.get(j));
                            competitors.set(j, tc);
                        }
                        break;
                    case Constants.VOTE_TYPE_VOTE:
                        if(competitors.get(i).compareTo(competitors.get(j), Constants.VOTE_TYPE_VOTE) < 0) {
                            tc = competitors.get(i);
                            competitors.set(i, competitors.get(j));
                            competitors.set(j, tc);
                        }
                        break;
                    case Constants.VOTE_TYPE_SCORE:
                        if(competitors.get(i).compareTo(competitors.get(j), Constants.VOTE_TYPE_SCORE) < 0) {
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
        /* 开始投票后，系统将停止报名 */
        if(v == null || !v.isFinished()) {
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
        if(v == null || !v.isFinished()) {
            return failure("报名已关闭!");
        }

        Competitor oc = competitorRepo.findByPhone(form.getPhone());
        if(oc != null) {
            return failure("您已经报过名了!");
        }

        Competitor c = new Competitor();
        c.setName(form.getName());
        c.setSex(form.getSex());
        c.setPhone(form.getPhone());
        c = competitorRepo.save(c);

        v.addCompetitor(c);
        voteRepo.save(v);
        return ok("报名成功");
    }

    /** 进入投票页面 **/
    @RequestMapping("/vote/{voteId}/competitor/{competitorId}")
    public String votePage(@PathVariable Long voteId,@PathVariable Long competitorId) {
        Vote v = voteRepo.findByIdAndStatus(voteId, Status.ACTIVE);
        if(v == null || v.isFinished()) {
            return "/wap/votes/error";
        }

        Competitor c = competitorRepo.findOne(competitorId);
        if(c == null) {
            return "/wap/votes/error";
        }
        DecimalFormat df = new DecimalFormat(Constants.DECIMAL_FORMAT);  
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
     * 投票
     */
    @RequestMapping(value = "/voting", method = RequestMethod.POST)
    @ResponseBody
    public Object voting(VotingForm voteForm) {
        /** 判断活动是否关闭 **/
        Vote v = voteRepo.findByIdAndStatus(voteForm.getVoteId(), Status.ACTIVE);
        if(v == null || v.isFinished()) {
            return failure("表决通过关闭");
        }

        /** 验证参数 **/
        Competitor c = competitorRepo.findOne(voteForm.getCompetitorId());
        VoteResult vr = verifyParameter(v, c, voteForm);
        if(vr == null) {
            return failure("表决失败,参数有误或者您已经表决过了！");
        }

        /** 新增投票记录 **/
        VoteRecord vrc = new VoteRecord(v, c, voteForm.getPhone());
        vrc.setCheckIn(true);
        voteRecordRepo.save(vrc);

        // TODO ：枷锁，考虑并发
        switch(voteForm.getVoteType()) {
            case Constants.VOTE_TYPE_CHECK:
                if(voteForm.isAddIn()) {
                    vr.setCheckInCount(vr.getCheckInCount() + 1);
                } else if(voteForm.isAddOut()) {
                    vr.setCheckOutCount(vr.getCheckOutCount() + 1);
                }
                break;
            case Constants.VOTE_TYPE_VOTE:
                vr.setVoteCount(vr.getVoteCount() + 1);
                break;
            case Constants.VOTE_TYPE_SCORE:
                if(vr.getScoreCount() == null || vr.getScoreCount() == 0) {
                    vr.setScoreCount(Double.valueOf(voteForm.getScore()));
                } else {
                    vr.setScoreCount(vr.getScoreCount() + Double.valueOf(voteForm.getScore()));
                }
                //记录投票次数，用户计算平均分
                vr.setVoteTimes(vr.getVoteTimes() + 1);
                break;
            default:
                break;
        }
        voteResultRepo.save(vr);

        return ok("表决成功");
    }

    /**
     * 验证投票请求参数
     * @param v 活动
     * @param c 选手
     * @param phone 投票者电话
     * @return 投票记录（如果参数不合法，则返回null）
     */
    public VoteResult verifyParameter(Vote v, Competitor c, VotingForm voteForm) {
        if(v == null || c == null || voteForm == null || voteForm.getPhone() == null) {
            return null;
        }
        Pattern p = Pattern.compile(Constants.VALIDATE_PATTERN_PHONE);
        Matcher m = p.matcher(voteForm.getPhone());
        if(!m.matches()) {
            return null;
        }
        VoteRecord vrc = voteRecordRepo.findByVoteAndCompetitorAndPhone(v, c, voteForm.getPhone());
        if(vrc != null) {
            return null;
        }
        /** 针对打分类的投票活动进行判断 **/
        if(voteForm.getVoteType() != null && voteForm.getVoteType().equals(Constants.VOTE_TYPE_SCORE) && voteForm.getScore() > v.getScoreSystem()) {
            return null;
        }

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
