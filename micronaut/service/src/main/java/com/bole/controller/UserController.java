package com.bole.controller;

import com.bole.client.Account;
import com.bole.client.User;
import com.bole.client.feign.UserFeignClientBuilder;
import com.bole.domain.UserAccountInfo;
import com.bole.domain.UserAccountResponse;
import com.bole.domain.UserInfo;
import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import com.bole.security.auth.AuthRequest;
import com.bole.security.auth.AuthorisationService;
import com.bole.service.StatsService;
import com.bole.service.UserAccountService;
import feign.Feign;
import feign.Logger;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.oauth2.endpoint.authorization.request.ResponseType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.Optional;


@Slf4j
@Controller("/micronaut/")
public class UserController {

    @Inject
    UserAccountService userAccountService;

    @Inject
    AuthorisationService authorisationService;

    @Operation(summary = "Retrieve user account details for authorized users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Body json supplied invalid ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid supplied credentials",
                    content = @Content)
    })

    @Post(value = "/useraccount/", consumes = "application/json", produces = "application/json")
    public HttpResponse<Optional<UserAccountResponse>> userAccount(@Body @Valid AuthRequest request){
        log.info("useraccount - request received with request body: " + request.toString());
        try {

            if(StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword())) {
                throw new BadRequestException("invalid username or password");
            }

            String token = authorisationService.obtainAuthToken(request.getUsername(), request.getPassword());

            User user = UserFeignClientBuilder.createClientWithInterceptor(User.class,"http://localhost:8898/micronaut/",token);

            UserInfo userInfo = user.findUserInfo();

            Account account = UserFeignClientBuilder.createRetryClientWithInterceptor(Account.class,"http://localhost:8899/micronaut/",token);

            UserAccountInfo userAccountInfo = account.findAccountInfo();


                    Optional<UserAccountResponse> userAccountResponse = userAccountService.getAccountInfo(
                            userAccountInfo, userInfo
            );

            HttpResponse<Optional<UserAccountResponse>> response = HttpResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userAccountResponse);

            return response;
        }catch ( UnAuthorizedException ex) {
            return HttpResponse.unauthorized();
        }
        catch ( BadRequestException ex) {
            return HttpResponse.badRequest();
        }
    }

}


