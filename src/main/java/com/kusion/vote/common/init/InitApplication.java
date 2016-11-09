package com.kusion.vote.common.init;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.kusion.vote.application.models.Config;
import com.kusion.vote.application.repos.ConfigRepo;
import com.kusion.vote.common.configs.Constants;

@Component
public class InitApplication implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ConfigRepo configJpaRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null) {
            Config initalize = configJpaRepo.findBySection(Constants.CONFIG_INITIALIZED);
            if(initalize == null || initalize.getValue() == "TRUE") {
                Config init = new Config();
                init.setSection(Constants.CONFIG_INITIALIZED);
                init.setName("init");
                init.setValue("TRUE");
                init.setNote("系统初始化");

                Config admin = new Config();
                admin.setSection(Constants.CONFIG_ADMIN_AUTH);
                admin.setName("admin");
                admin.setValue("admin");
                admin.setNote("默认管理员账号");

                Config seoTitle = new Config();
                seoTitle.setSection(Constants.SETTING_KEY_SEO);
                seoTitle.setName("title");
                seoTitle.setValue("在线表决系统");
                seoTitle.setNote("标题");

                Config seoKeywords = new Config();
                seoKeywords.setSection(Constants.SETTING_KEY_SEO);
                seoKeywords.setName("keywords");
                seoKeywords.setValue("在线表决系统");
                seoKeywords.setNote("关键字");

                Config seoDescriptions = new Config();
                seoDescriptions.setSection(Constants.SETTING_KEY_SEO);
                seoDescriptions.setName("descriptions");
                seoDescriptions.setValue("在线表决系统");
                seoDescriptions.setNote("描述");

                configJpaRepo.save(Arrays.asList(init, admin, seoTitle, seoKeywords, seoDescriptions));
            }
        }
    }

}
