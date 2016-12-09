package com.kusion.vote.application.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.Competitor;
import com.kusion.vote.application.models.Vote;
import com.kusion.vote.application.models.VoteRecord;

public interface VoteRecordRepo extends JpaRepository<VoteRecord, Long> {
    VoteRecord findByVoteAndCompetitor(Vote v, Competitor c);

    VoteRecord findByVoteAndCompetitorAndPhone(Vote v, Competitor c, String phone);
}
