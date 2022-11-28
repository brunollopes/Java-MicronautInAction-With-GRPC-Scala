package com.bole.client;

import com.bole.domain.UserAccountInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Account Client
 */

@FeignClient(value = "clientAccount", url = "http://localhost:8899/springboot")
public interface Account {

    @Retryable(value = feign.RetryableException.class,maxAttempts = 3, backoff = @Backoff(delay = 100))
    @RequestMapping(method = RequestMethod.GET, value = "/account/" , produces = "application/json")
    UserAccountInfo findAccountInfo(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
