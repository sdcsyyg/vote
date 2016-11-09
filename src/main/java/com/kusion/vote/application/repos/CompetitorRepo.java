package com.kusion.vote.application.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.Competitor;

public interface CompetitorRepo extends JpaRepository<Competitor, Long> {
    Competitor findByPhone(String phone);
}
