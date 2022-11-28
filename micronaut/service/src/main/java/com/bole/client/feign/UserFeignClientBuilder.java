package com.bole.client.feign;


import com.bole.domain.UserInfo;
import com.bole.exception.Custom5xxErrorDecoder;
import com.bole.security.auth.AuthFeignRequestInterceptor;
import com.bole.security.auth.AuthorisationServiceImpl;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class UserFeignClientBuilder {
    private UserInfo bookClient = createClient(UserInfo.class, "http://localhost:8898/micronaut/user/");

    public static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }

    public static <T> T createClientWithInterceptor(Class<T> type, String uri, String token) {
        return Feign.builder()
                .requestInterceptor(new AuthFeignRequestInterceptor(token))
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.HEADERS)
                .target(type, uri);
    }

    public static <T> T createRetryClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .retryer(new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5))
                .errorDecoder(new Custom5xxErrorDecoder())
                .target(type, uri);
    }

    public static <T> T createRetryClientWithInterceptor(Class<T> type, String uri,String token) {
        return Feign.builder()
                .requestInterceptor(new AuthFeignRequestInterceptor(token))
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .retryer(new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5))
                .errorDecoder(new Custom5xxErrorDecoder())
                .target(type, uri);
    }
}
