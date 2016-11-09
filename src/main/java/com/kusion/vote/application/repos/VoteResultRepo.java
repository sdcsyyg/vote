package com.kusion.vote.application.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.Competitor;
import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.models.VoteResult;

public interface VoteResultRepo extends JpaRepository<VoteResult, Long> {
    VoteResult findByVoteAndCompetitor(Vote v, Competitor c);
}
