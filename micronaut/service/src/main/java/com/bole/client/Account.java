package com.bole.client;

import com.bole.domain.UserAccountInfo;
import feign.Headers;
import feign.RequestLine;


/**
 * Account Client
 */

@Headers("Accept: application/json")
//@Retryable(includes = {UnAuthorizedException.class},attempts = "3", delay = "2s")
public interface Account {

    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET account/")
    UserAccountInfo findAccountInfo();

}
