package com.kusion.vote.application.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.WxUser;

public interface WxUserRepo extends JpaRepository<WxUser, Long>{

    List<WxUser> findByOpenid(String openid);
}
