package com.bole.security.auth;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import com.bole.grpc.client.LoginManager;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthorisationServiceImpl implements AuthorisationService{

    @Inject
    LoginManager loginManager;


    @Override
    public String obtainAuthToken(String username, String password) throws BadRequestException, UnAuthorizedException {

        String token = loginManager.obtainLoginToken(username, password);

        return token;
    }
}
