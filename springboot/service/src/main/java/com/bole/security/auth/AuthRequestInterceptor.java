package com.bole.security.auth;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthRequestInterceptor implements RequestInterceptor {

    private AuthorisationService authTokenService;

    public AuthRequestInterceptor(AuthorisationService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    public void apply(RequestTemplate template) {
        System.out.println("AuthRequestInterceptor-apply");
        try {
            template.header("Authorisation", "Bearer " + authTokenService.obtainAuthToken("bla","foo"));
        } catch (BadRequestException | UnAuthorizedException e) {
            e.printStackTrace();
        }
    }
}
