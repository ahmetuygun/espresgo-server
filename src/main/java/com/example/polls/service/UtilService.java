package com.example.polls.service;

import com.example.polls.model.Util;
import com.example.polls.repository.UtilRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService {


    @Autowired
    UtilRepository utilRepository;

    public boolean set(UserPrincipal currentUser, String key, String value) {

        try {
            Util location = utilRepository.findByName(key);
            if(location!=null){
                location.setValue(value);
                location = utilRepository.save(location);
            }else{
                location = utilRepository.save(Util.builder().name(key).value(value).build());
            }
            return AppConstants.CLOSE.equals(location.getValue());
        }catch (Exception e){
            return false;
        }
    }

    public boolean get(String key, String value) {

        try {
            Util location = utilRepository.findByName(key);
            if(location!=null){
                return value.equals(location.getValue());
            }
            return false;
        }catch (Exception e){
            return false;
        }

    }

}
