package com.kusion.vote.wap.forms;

public class VotingForm {

    private long voteId;
    private long competitorId;
    private String phone;
    private boolean addIn;
    private boolean addOut;
    private boolean vote;
    private double score;
    private String voteType;

    public long getVoteId() {
        return voteId;
    }
    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }
    public long getCompetitorId() {
        return competitorId;
    }
    public void setCompetitorId(long competitorId) {
        this.competitorId = competitorId;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isAddIn() {
        return addIn;
    }
    public void setAddIn(boolean addIn) {
        this.addIn = addIn;
    }
    public boolean isAddOut() {
        return addOut;
    }
    public void setAddOut(boolean addOut) {
        this.addOut = addOut;
    }
    public boolean isVote() {
        return vote;
    }
    public void setVote(boolean vote) {
        this.vote = vote;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getVoteType() {
        return voteType;
    }
    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

}
