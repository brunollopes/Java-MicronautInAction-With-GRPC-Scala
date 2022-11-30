package com.bole.service;

import com.bole.client.User;
import com.bole.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    User userClient;

    @Async
    public CompletableFuture<UserInfo> getUserInfo(String token) {
        try {
            System.out.println(
                    "Current Thread user Name: "
                            + Thread.currentThread().getName());
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(userClient.findUserInfo(token).getBody());
    }

}
