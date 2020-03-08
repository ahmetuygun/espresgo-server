package com.example.polls.service;


import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;


@Service
public class SmsService {


    @Value("${twilio.serviceSid}")
    private String serviceSid;


    public String createSid() {
        try {
            return com.twilio.rest.verify.v2.Service.creator("Espresgo").
                    setCodeLength(4).createAsync().get().getSid();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public boolean sendVerificationCode(String to) {
        try {
            if (serviceSid == null) {
                serviceSid = createSid();
            }
            Verification.creator(serviceSid, "+90"+to, "sms").setLocale("tr").create();
            return true;
        } catch (Exception e) {
             throw e;
        }
    }

    public boolean checkVerification(String to, String verficationCode) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(serviceSid, verficationCode).setTo("+90"+to).create();
            return verificationCheck.getStatus().equals("approved");
        } catch (Exception e) {
            return true;
        }
    }


}
