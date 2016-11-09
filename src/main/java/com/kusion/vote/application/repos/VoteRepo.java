package com.kusion.vote.application.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.Vote;
import com.kusion.vote.common.enums.Status;

public interface VoteRepo extends JpaRepository<Vote, Long> {

    Page<Vote> findByStatus(Pageable pageablea, Status active);

    List<Vote> findByStatus(Status active);

    Vote findByIdAndStatus(Long voteId, Status active);

}
