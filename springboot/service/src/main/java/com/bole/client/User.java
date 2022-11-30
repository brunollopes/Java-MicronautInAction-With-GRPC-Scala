package com.bole.client;

import com.bole.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User Client
  */
@FeignClient(value = "clientUser", url = "http://localhost:8898/springboot", fallback = UserFallback.class)
public interface User {

    @RequestMapping(method = RequestMethod.GET, value = "/user/", produces = "application/json")
    ResponseEntity<UserInfo> findUserInfo(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
