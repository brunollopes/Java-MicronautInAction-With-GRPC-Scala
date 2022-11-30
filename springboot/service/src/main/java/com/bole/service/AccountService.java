package com.bole.service;

import com.bole.client.Account;
import com.bole.domain.UserAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountService {

    @Autowired
    Account accountClient;

    @Async
    public CompletableFuture<UserAccountInfo> getAccountInfo(String token) {
        try {
            System.out.println(
                    "Current Thread account Name: "
                            + Thread.currentThread().getName());
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(accountClient.findAccountInfo(token).getBody());
    }

}
