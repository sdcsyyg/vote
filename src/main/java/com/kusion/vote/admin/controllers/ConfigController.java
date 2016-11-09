package com.kusion.vote.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.admin.forms.ConfigForm;
import com.kusion.vote.application.models.Config;
import com.kusion.vote.application.repos.ConfigRepo;
import com.kusion.vote.common.configs.Constants;

@Controller
@RequestMapping("/admin/cog")
public class ConfigController extends AdminBaseController {

    @Autowired
    ConfigRepo configRepo;

    @RequestMapping("/base")
    public String point() {
        Config cpm = configRepo.findByName(Constants.CONFIG_POINT_MUV);
        Config cpr = configRepo.findByName(Constants.CONFIG_POINT_RATE);
        request().setAttribute("cpm", cpm);
        request().setAttribute("cpr", cpr);
        return pc("/cog/base");
    }

    @RequestMapping(value = "/save", method= RequestMethod.POST)
    @ResponseBody
    public Object save(ConfigForm form) {
        String pmvu = form.getPmuv();

        Config cpm = configRepo.findByName(Constants.CONFIG_POINT_MUV);
        if(cpm == null) {
            cpm = new Config();
            cpm.setName(Constants.CONFIG_POINT_MUV);
        }
        cpm.setValue(pmvu);
        configRepo.save(cpm);

        String prate = form.getPrate();
        Config cpr = configRepo.findByName(Constants.CONFIG_POINT_RATE);
        if(cpr == null) {
            cpr = new Config();
            cpr.setName(Constants.CONFIG_POINT_RATE);
        }
        cpr.setValue(prate);
        configRepo.save(cpr);

        return ok("修改成功");
    }
}
