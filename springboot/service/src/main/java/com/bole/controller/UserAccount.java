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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * API UserAccount
*/
@Slf4j
@RestController
public class UserAccount {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
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

    @PostMapping(value = "/springboot/useraccount/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  userAccount(@RequestBody @Valid AuthRequest request) {
       log.info("useraccount - request received with request body: " + request.toString());
       try {

           if (Strings.isBlank(request.getUsername()) || Strings.isBlank(request.getPassword())) {
               throw new BadRequestException("invalid username or password");
           }

           String token = authorisationService.obtainAuthToken(request.getUsername(), request.getPassword());

           final long startTime = System.currentTimeMillis();

           CompletableFuture<UserAccountInfo> userAccountInfo = accountService.getAccountInfo(token);
           CompletableFuture<UserInfo> userInfo = userService.getUserInfo(token);

           System.out.println(
                   "Current main over Account and Service Thread Name : "
                           + Thread.currentThread().getName());

           Optional<UserAccountResponse> userAccountResponse = userAccountService.getAccountInfo(
                   userAccountInfo.get(),userInfo.get()
           );

           final long endTime = System.currentTimeMillis();
           final float diffTime = (endTime - startTime) / 1000F;
           System.out.println("Rest feign call Total time: " + diffTime);

           if (userAccountResponse.isEmpty()) {
               throw new BadCredentialsException("Bad Credentials");
           }

           return ResponseEntity.ok().body(userAccountResponse);
       } catch (BadCredentialsException | UnAuthorizedException ex) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       } catch (BadRequestException ex) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
       catch (ExecutionException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       } catch (InterruptedException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
}


