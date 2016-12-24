package com.kusion.vote.application.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.kusion.vote.common.models.AbstractModel;

@Entity
public class VoteResult extends AbstractModel {

    private static final long serialVersionUID = -1879497659939645216L;

    @ManyToOne
    private Vote vote;

    @ManyToOne
    private Competitor competitor;


    /** 赞成票数 **/
    private long checkInCount = 0L;

    /** 反对票数 **/
    private long checkOutCount = 0L;

    /** 票数【投票累的】 **/
    private long voteCount = 0L;

    /** 总分【打分制】 **/
    private Double scoreCount;

    /** 打分次数（用求平均数） **/
    private long voteTimes = 0L;

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public long getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(long checkInCount) {
        this.checkInCount = checkInCount;
    }

    public long getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(long checkOutCount) {
        this.checkOutCount = checkOutCount;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public Double getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Double scoreCount) {
        this.scoreCount = scoreCount;
    }

    public long getVoteTimes() {
        return voteTimes;
    }

    public void setVoteTimes(long voteTimes) {
        this.voteTimes = voteTimes;
    }

}
