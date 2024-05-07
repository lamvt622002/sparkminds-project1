package com.example.project1.services.impl;

import com.example.project1.services.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Override
    public void sendMessage(String phoneNumber,String body){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+12253618811"),
                body
        ).create();
    }
}
