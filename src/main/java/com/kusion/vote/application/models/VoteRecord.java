package com.kusion.vote.application.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.kusion.vote.common.models.AbstractModel;

@Entity
public class VoteRecord extends AbstractModel {

    private static final long serialVersionUID = -1879497659939645216L;

    @ManyToOne
    private Vote vote;

    @ManyToOne
    private Competitor competitor;

    /** 赞成票数 **/
    private boolean checkIn;

    /** 反对票数 **/
    private boolean checkOut;

    /** 打分 **/
    private boolean score;
 
    /** 投票 **/
    private boolean voting;

    private String phone;

    public VoteRecord(Vote v, Competitor c, String phone) {
        this.vote = v;
        this.competitor = c;
        this.phone = phone;
    }

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

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public boolean isCheckOut() {
        return checkOut;
    }

    public void setCheckOut(boolean checkOut) {
        this.checkOut = checkOut;
    }

    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    public boolean isVoting() {
        return voting;
    }

    public void setVoting(boolean voting) {
        this.voting = voting;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
