package com.bole.grpc.client;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Singleton
public class LoginManager {

    public String obtainLoginToken(String username, String password) throws BadRequestException,UnAuthorizedException {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BadRequestException("invalid username or password");
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub stub
                = LoginServiceGrpc.newBlockingStub(channel);

        LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build());

        channel.shutdown();

        if(StringUtils.isEmpty(loginResponse.getToken())){
            throw new UnAuthorizedException("Invalid Credentials");
        }
        return loginResponse.getToken();

    }

}
