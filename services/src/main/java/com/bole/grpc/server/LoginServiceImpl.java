package com.bole.grpc.server;

import com.bole.security.auth.JwtTokenProvider;
import services.LoginResponse;
import services.LoginServiceGrpc;

import java.util.ArrayList;

public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    private String username="bla";

    @Override
    public void login(services.LoginRequest request,
                      io.grpc.stub.StreamObserver<LoginResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        LoginResponse loginResponse;

        if(!username.equals(this.username)) {
            loginResponse = LoginResponse.newBuilder().setToken("").build();
        } else  {
            String token = jwtTokenProvider.generateToken(username,password,new ArrayList<>(){});

            loginResponse = LoginResponse.newBuilder().setToken(token).build();

        }

        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();

    }

}
