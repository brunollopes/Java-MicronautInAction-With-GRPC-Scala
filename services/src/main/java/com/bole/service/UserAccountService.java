package com.bole.service;

import com.bole.domain.UserAccountInfo;
import com.bole.domain.UserAccountResponse;
import com.bole.domain.UserInfo;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserAccountService {

    public Optional<UserAccountResponse> getAccountInfo(UserAccountInfo accountInfo, UserInfo userInfo) {
        return Optional.of(new UserAccountResponse(accountInfo, userInfo));
    }

}
