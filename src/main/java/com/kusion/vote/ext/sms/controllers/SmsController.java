package com.kusion.vote.ext.sms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kusion.vote.application.repos.UserRepo;
import com.kusion.vote.common.configs.Constants;
import com.kusion.vote.common.controllers.RestController;
import com.kusion.vote.common.utils.NumberUtil;
import com.kusion.vote.common.utils.RequestUtil;
import com.kusion.vote.ext.sms.models.SmsOrder;
import com.kusion.vote.ext.sms.repos.SmsOrderRepo;
import com.kusion.vote.ext.sms.tpls.Callback;
import com.kusion.vote.ext.sms.utils.Sms;

@Controller
@RequestMapping({"/ma/sms", "/web/sms"})
public class SmsController extends RestController {

    @Autowired
    SmsOrderRepo smsOrderRepo;

    @Autowired
    UserRepo userRepo;

    @RequestMapping(value = "/send/{phone}", method = RequestMethod.GET)
    @ResponseBody
    public Object sendRegister(@PathVariable String phone) {

        if(!phone.matches(Constants.VALIDATE_PATTERN_PHONE)) {
            return failure("请输入正确的手机号码");
        }

        if(!userRepo.findByPhone(phone).isEmpty()) {
            return failure("该手机号码已被绑定");
        }

        String randcode = getRandcode();
        Callback smsResMsg = Sms.sendRandcode(phone, randcode);

        if(smsResMsg != null && "000000".equals(smsResMsg.getRespCode())) {
            log.info("短信验证码发送成功：" + phone + " [" + randcode + "]");
        }else {
            log.error("短信验证码发送失败：" + phone + " [" + randcode + "]");
            return failure("短信验证码发送失败，请稍候再试");
        }

        SmsOrder order = new SmsOrder();
        order.setPhone(phone);
        order.setIp(RequestUtil.getIp(request()));
        order.setContent(randcode);
        order.setRespCode(smsResMsg.getRespCode());
        order.setSmsId(smsResMsg.getSmsId());
        order.setCreateDate(smsResMsg.getCreateDate());
        smsOrderRepo.save(order);

        return ok("短信验证码发送成功");
    }

    @RequestMapping(value = "/send/forget/{phone}", method = RequestMethod.GET)
    @ResponseBody
    public Object sendForget(@PathVariable String phone) {

        if(!phone.matches(Constants.VALIDATE_PATTERN_PHONE)) {
            return failure("请输入正确的手机号码");
        }

        if(userRepo.findByPhone(phone).isEmpty()) {
            return failure("该手机号码尚未注册");
        }

        String randcode = getRandcode();
        Callback smsResMsg = Sms.sendRandcode(phone, randcode);

        if(smsResMsg != null && "000000".equals(smsResMsg.getRespCode())) {
            log.info("短信验证码发送成功：" + phone + " [" + randcode + "]");
        }else {
            log.error("短信验证码发送失败：" + phone + " [" + randcode + "]");
            return failure("短信验证码发送失败，请稍候再试");
        }

        SmsOrder order = new SmsOrder();
        order.setPhone(phone);
        order.setIp(RequestUtil.getIp(request()));
        order.setContent(randcode);
        order.setRespCode(smsResMsg.getRespCode());
        order.setSmsId(smsResMsg.getSmsId());
        order.setCreateDate(smsResMsg.getCreateDate());
        smsOrderRepo.save(order);

        return ok("短信验证码发送成功");
    }

    private static String getRandcode() {
        return NumberUtil.getRandcode(4);
    }
}
