package com.bole.client;

import com.bole.domain.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements User {

    @Override
    public UserInfo findUserInfo(String authorizationHeader) {
        return new UserInfo();
    }

}

