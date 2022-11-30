package com.bole.client;

import com.bole.domain.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFallback implements User {

    @Override
    public ResponseEntity<UserInfo> findUserInfo(String authorizationHeader) {
        return ResponseEntity.of(Optional.of(new UserInfo()));
    }

}

