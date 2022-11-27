package com.bole.client;

import com.bole.domain.UserInfo;


public class UserFallback implements User {

    @Override
    public UserInfo findUserInfo() {
        return new UserInfo();
    }

}

