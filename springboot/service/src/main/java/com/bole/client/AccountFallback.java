package com.bole.client;

import com.bole.domain.UserAccountInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountFallback implements Account {

    @Override
    public ResponseEntity<UserAccountInfo> findAccountInfo(String authorizationHeader) {
        return ResponseEntity.of(Optional.of(new UserAccountInfo()));
    }

}

