package com.bole.security.auth;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthFeignRequestInterceptor implements RequestInterceptor {

    private String token;

    public AuthFeignRequestInterceptor(String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate template) {
            template.header("Authorization", this.token);

    }
}
