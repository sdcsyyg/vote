package com.kusion.vote.ext.sms.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kusion.vote.ext.sms.models.SmsOrder;

public interface SmsOrderRepo extends CrudRepository<SmsOrder, Long>{

    List<SmsOrder> findByPhoneAndContent(String phone, String content);

    List<SmsOrder> findByPhoneAndContentAndRespCodeOrderByCreatedAtDesc(String phone, String content, String respCode);
}
