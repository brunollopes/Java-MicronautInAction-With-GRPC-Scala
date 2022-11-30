package com.bole.controller;

import com.bole.domain.UserAccountInfo;
import com.bole.domain.UserAccountResponse;
import com.bole.domain.UserInfo;
import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import com.bole.security.auth.AuthRequest;
import com.bole.security.auth.AuthorisationService;
import com.bole.service.AccountService;
import com.bole.service.UserAccountService;
import com.bole.service.UserService;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Controller("/micronaut/")
public class UserController {

    @Inject
    UserAccountService userAccountService;

    @Inject
    AuthorisationService authorisationService;

    @Inject
    UserService userService;

    @Inject
    AccountService accountService;

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

            final long startTime = System.currentTimeMillis();

            CompletableFuture<UserAccountInfo> userAccountInfo = accountService.getAccountInfo(token);
            CompletableFuture<UserInfo> userInfo = userService.getUserInfo(token);

            Optional<UserAccountResponse> userAccountResponse = userAccountService.getAccountInfo(
                    userAccountInfo.get(),userInfo.get()
            );


            final long endTime = System.currentTimeMillis();
            final float diffTime = (endTime - startTime) / 1000F;
            System.out.println("Rest feign call Total time: " + diffTime);


            HttpResponse<Optional<UserAccountResponse>> response = HttpResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userAccountResponse);

            return response;
        }catch ( UnAuthorizedException ex) {
            return HttpResponse.unauthorized();
        }
        catch ( BadRequestException ex) {
            return HttpResponse.badRequest();
        } catch (ExecutionException e) {
            return HttpResponse.serverError();
        } catch (InterruptedException e) {
            return  HttpResponse.serverError();
        }
    }

}


