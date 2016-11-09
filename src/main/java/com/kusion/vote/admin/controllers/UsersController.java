package com.kusion.vote.admin.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.admin.forms.UserForm;
import com.kusion.vote.application.models.User;
import com.kusion.vote.application.repos.UserRepo;
import com.kusion.vote.common.utils.MD5Util;

@Controller
@RequestMapping("/admin/users")
public class UsersController extends AdminBaseController {

    @Autowired
    UserRepo userRepo;

    @RequestMapping("/{page}")
    public String render(@PathVariable String page) {
        return pc("/users/" + page);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public Page<User> page(@RequestParam String order, @RequestParam Integer limit, @RequestParam Integer offset) {
        Sort sort = new Sort(new Order(Direction.DESC, "id"));
        Pageable pageablea = new PageRequest(offset/limit, limit, sort);
        return userRepo.findAllByDisable(pageablea, false);
    }

    @RequestMapping(value = "/pageBlackList", method = RequestMethod.GET)
    @ResponseBody
    public Page<User> pageBlackList(@RequestParam String order, @RequestParam Integer limit, @RequestParam Integer offset) {
        Sort sort = new Sort(new Order(Direction.DESC, "id"));
        Pageable pageablea = new PageRequest(offset/limit, limit, sort);
        return userRepo.findAllByDisable(pageablea, true);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(UserForm form) {
        String realname = form.getRealname();
        if(StringUtils.isEmpty(realname)) {
            return failure("姓名不能为空");
        }
        String phone = form.getPhone();
        if(StringUtils.isEmpty(phone)) {
            return failure("电话号码不能为空");
        }
        if(!userRepo.findByPhone(phone).isEmpty()) {
            return failure("手机号码已被使用，请重新输入");
        }
        User user = new User();
        user.setNickname(form.getNickname());
        user.setRealname(realname);
        user.setPhone(phone);
        user.setGender(form.getSex());
        user.setQq(form.getQq());
        user.setWeixin(form.getWeixin());
        user.setPassword(MD5Util.MD5(phone.substring(5)));
        userRepo.save(user);
        return ok("保存成功");
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id) { 
        User user = userRepo.findOne(id);
        if(user == null) {
        	return pc("/users/list");
        }
        request().setAttribute("user", user);
        return pc("/users/edit");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, UserForm form) {
        User user = userRepo.findOne(id);
        if(user == null) {
            return failure("您要修改的信息不存在");
        }
        String realname = form.getRealname();
        if(StringUtils.isEmpty(realname)) {
            return failure("姓名不能为空");
        }
        String phone = form.getPhone();
        if(StringUtils.isEmpty(phone)) {
            return failure("电话号码不能为空");
        }
        List<User> users = userRepo.findByPhone(phone);
        if(!users.isEmpty() && users.get(0).getId() != id) {
            return failure("手机号码已被使用，请重新输入");
        }
        user.setNickname(form.getNickname());
        user.setRealname(realname);
        user.setGender(form.getSex());
        user.setPhone(phone);
        user.setQq(form.getQq());
        user.setWeixin(form.getWeixin());
        userRepo.save(user);
        return ok("修改成功");
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object disable(@PathVariable Long id) {
        User user = userRepo.findOne(id);
        if(user != null && !user.isDisable()){
           user.setDisable(true);
           userRepo.save(user);
        }
        return ok("禁用成功");
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object enable(@PathVariable Long id) {
        User user = userRepo.findOne(id);
        if(user != null && user.isDisable()){
           user.setDisable(false);
           userRepo.save(user);
        }
        return ok("启用成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        User user = userRepo.findOne(id);
        if(user != null){
           userRepo.delete(user);
        }
        return ok("删除成功");
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id) {
        User user = userRepo.findOne(id);
        if(user == null) {
            return pc("/users/list");
        }
        request().setAttribute("user", user);
        return pc("/users/view");
    }
}
