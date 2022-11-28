package com.bole.client;

import com.bole.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User Client
  */
@FeignClient(value = "clientUser", url = "http://localhost:8898/springboot")
public interface User {

    @RequestMapping(method = RequestMethod.GET, value = "/user/", produces = "application/json")
    UserInfo findUserInfo(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
