package com.bole.security.auth;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import com.bole.grpc.client.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorisationServiceImpl implements AuthorisationService{

    @Autowired
    LoginManager loginManager;


    @Override
    public String obtainAuthToken(String username, String password) throws BadRequestException, UnAuthorizedException {

        String token = loginManager.obtainLoginToken(username, password);

        return token;
    }
}
