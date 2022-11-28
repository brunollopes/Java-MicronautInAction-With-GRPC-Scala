package com.bole.client;

import com.bole.domain.UserAccountInfo;
import org.springframework.stereotype.Component;

@Component
public class AccountFallback implements Account {

    @Override
    public UserAccountInfo findAccountInfo(String authorizationHeader) {
        return new UserAccountInfo();
    }

}

