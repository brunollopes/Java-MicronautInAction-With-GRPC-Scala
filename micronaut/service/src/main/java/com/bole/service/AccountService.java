package com.bole.service;

import com.bole.client.Account;
import com.bole.client.feign.UserFeignClientBuilder;
import com.bole.domain.UserAccountInfo;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

import java.util.concurrent.CompletableFuture;

@Singleton
public class AccountService {

    @Async
    public CompletableFuture<UserAccountInfo> getAccountInfo(String token) {
        // uncomment if you want to understand the async threads
//        try {
//            System.out.println(
//                    "Current Thread account Name: "
//                            + Thread.currentThread().getName());
//            Thread.currentThread().sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Account accountClient = UserFeignClientBuilder.createRetryClientWithInterceptor(Account.class,"http://localhost:8899/micronaut/",token);

        return CompletableFuture.completedFuture(accountClient.findAccountInfo());
    }

}
