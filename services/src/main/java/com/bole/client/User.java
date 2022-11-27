package com.bole.client;

import com.bole.domain.UserInfo;
import feign.Headers;
import feign.RequestLine;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;

import static io.micronaut.http.HttpHeaders.ACCEPT;

/**
 * User Client
  */
//@Client("http://localhost:8898/micronaut/user/")
//@Headers({
//        @Header(name="Content-type",value="application/json"),
//        @Header(name=ACCEPT,value="application/json")
//})
//@Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")


@Headers("Accept: application/json")
public interface User {

    //@Get(produces = "application/json")
    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET user/")
    UserInfo findUserInfo();

}
