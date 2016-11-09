package com.kusion.vote.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kusion.vote.application.models.Config;
import com.kusion.vote.application.repos.ConfigRepo;
import com.kusion.vote.common.configs.Constants;

@Service
public class ConfigService {

    @Autowired
    ConfigRepo configRepo;

    public Long getPointMuv() {
        return getLongByName(Constants.CONFIG_POINT_MUV);
    }

    public Long getPointRate() {
        return getLongByName(Constants.CONFIG_POINT_RATE);
    }

    public Long getCourseMinhour() {
        return getLongByName(Constants.CONFIG_COURSE_MINHOUR);
    }

    private Long getLongByName(String name) {
        Config config = configRepo.findByName(name);
        return Long.valueOf(config.getValue());
    }
}
