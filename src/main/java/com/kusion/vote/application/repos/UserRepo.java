package com.kusion.vote.application.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kusion.vote.application.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByPhone(String phone);

    Page<User> findAllByDisable(Pageable pageablea, boolean b);

    List<User> findByOpenid(String openid);
}
