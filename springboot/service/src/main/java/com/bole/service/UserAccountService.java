package com.bole.service;

import com.bole.domain.UserAccountInfo;
import com.bole.domain.UserAccountResponse;
import com.bole.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    public Optional<UserAccountResponse> getAccountInfo(UserAccountInfo accountInfo, UserInfo userInfo) {
        return Optional.of(new UserAccountResponse(accountInfo, userInfo));
    }

}
