package com.bole.grpc.client;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Component
public class LoginManager {

    public String obtainLoginToken(String username, String password) throws BadRequestException,UnAuthorizedException {

        if(Strings.isBlank(username) || Strings.isBlank(password)) {
            throw new BadRequestException("invalid username or password");
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub stub
                = LoginServiceGrpc.newBlockingStub(channel);

        LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build());

        channel.shutdown();

        if(Strings.isBlank(loginResponse.getToken())){
            throw new UnAuthorizedException("Invalid Credentials");
        }
        return loginResponse.getToken();

    }

}
