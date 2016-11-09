package com.kusion.vote.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.application.models.Config;
import com.kusion.vote.application.repos.ConfigRepo;
import com.kusion.vote.common.configs.Constants;

/**
 * Created by shuai on 16/6/13.
 */
@Controller
@RequestMapping("/admin/settings")
public class SettingsController extends AdminBaseController {

    @Autowired
    ConfigRepo configRepo;

    @RequestMapping("/seo")
    public String list() {
        return pc("/settings/seo");
    }

    @RequestMapping(value = "/paged/seo", method = RequestMethod.GET)
    @ResponseBody
    public Page<Config> paged(@RequestParam String order,
                                @RequestParam Integer limit,
                                @RequestParam Integer offset) {
        Sort sort = new Sort(new org.springframework.data.domain.Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageablea = new PageRequest(offset / limit, limit, sort);
        Page<Config> configs = configRepo.findBySection(Constants.SETTING_KEY_SEO, pageablea);
        return configs;
    }

    @RequestMapping(value = "/update/seo/{id}/{value}",method = RequestMethod.GET)
    @ResponseBody
    public Object update(@PathVariable Long id, @PathVariable String value){
        Config cfg = configRepo.findOne(id);
        if(cfg == null) {
            if(cfg == null) {
                return failure("设置失败");
            }
        }
        cfg.setValue(value);
        configRepo.save(cfg);
        return ok("设置成功");
    }

}
