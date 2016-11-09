package com.kusion.vote.application.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusion.vote.common.models.AbstractModel;

@Entity
public class Competitor extends AbstractModel {

    private static final long serialVersionUID = -1879497659939645216L;

    private String name;

    private String sex;

    private String phone;

    @ManyToMany(mappedBy = "competitors")
    @JsonIgnore
    private List<Vote> votes;

    /** 赞成票数 **/
    @Transient
    private long checkInCount;

    /** 反对票数 **/
    @Transient
    private long checkOutCount;

    /** 票数【投票累的】 **/
    @Transient
    private long voteCount;

    /** 总分【打分制】 **/
    @Transient
    private Double scoreCount = 0.0;

    /** 平均分  **/
    @Transient
    private Double average = 0.0;

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

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

}
