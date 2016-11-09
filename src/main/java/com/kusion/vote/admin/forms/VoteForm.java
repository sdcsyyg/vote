package com.kusion.vote.admin.forms;


public class VoteForm {

    private String name;

    private String voteType;

    /** 分制：针对打分形式的 **/
    private int scoreSystem = 100;

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public int getScoreSystem() {
        return scoreSystem;
    }

    public void setScoreSystem(int scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
