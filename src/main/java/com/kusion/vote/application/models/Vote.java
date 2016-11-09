package com.kusion.vote.application.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.kusion.vote.common.models.AbstractModel;

@Entity
public class Vote extends AbstractModel {

    private static final long serialVersionUID = -5601912908897341378L;

    /** 参与者 **/
    @ManyToMany
    @JoinTable(name = "_vote_competitor",
    joinColumns = {@JoinColumn(name = "vote_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "competitor_id", referencedColumnName ="id")})
    private List<Competitor> competitors;

    private String name;

    private String voteType;

    /** 分制：针对打分形式的 **/
    private int scoreSystem = 100;

    public void addCompetitor(Competitor c) {
        this.competitors.add(c);
    }


    public Date getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    private Date finishAt;

    public int getScoreSystem() {
        return scoreSystem;
    }

    public void setScoreSystem(int scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> participators) {
        this.competitors = participators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getVoteType() {
        return voteType;
    }


    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

}
