package com.kusion.vote.ext.wx.repos;

import com.kusion.vote.ext.wx.models.MenuBtn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ReedMi on 2016/9/27.
 */
public interface MenuBtnRepo extends JpaRepository<MenuBtn, Long> {

    Page<MenuBtn> findByParent(MenuBtn mb, Pageable pageable);
}
