package com.bole.service;

import com.bole.client.User;
import com.bole.client.feign.UserFeignClientBuilder;
import com.bole.domain.UserInfo;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

import java.util.concurrent.CompletableFuture;

@Singleton
public class UserService {

    @Async
    public CompletableFuture<UserInfo> getUserInfo(String token) {
        // uncomment if you want to understand the async threads
//        try {
//            System.out.println(
//                    "Current Thread user Name: "
//                            + Thread.currentThread().getName());
//            Thread.currentThread().sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        User userClient = UserFeignClientBuilder.createClientWithInterceptor(User.class,"http://localhost:8898/micronaut/",token);

        return CompletableFuture.completedFuture(userClient.findUserInfo());
    }

}
