package com.bole.client;

import com.bole.domain.UserAccountInfo;


public class AccountFallback implements Account {

    @Override
    public UserAccountInfo findAccountInfo() {
        return new UserAccountInfo();
    }

}

