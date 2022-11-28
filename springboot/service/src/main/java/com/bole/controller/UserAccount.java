package com.bole.controller;

import com.bole.client.Account;
import com.bole.client.User;
import com.bole.domain.UserAccountInfo;
import com.bole.domain.UserAccountResponse;
import com.bole.domain.UserInfo;
import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;
import com.bole.security.auth.AuthRequest;
import com.bole.security.auth.AuthorisationService;
import com.bole.service.StatsService;
import com.bole.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * API UserAccount
*/
@RestController
public class UserAccount {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    User clientUser;

    @Autowired
    Account clientAccount;

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
    public ResponseEntity<?>  userAccount(@RequestBody @Valid AuthRequest request){
        try {

            if(Strings.isBlank(request.getUsername()) || Strings.isBlank(request.getPassword())) {
                throw new BadRequestException("invalid username or password");
            }

            String token = authorisationService.obtainAuthToken(request.getUsername(), request.getPassword());

            UserInfo userInfo = clientUser.findUserInfo(token);
            UserAccountInfo userAccountInfo = clientAccount.findAccountInfo(token);

            Optional<UserAccountResponse> userAccountResponse = userAccountService.getAccountInfo(
                 userAccountInfo, userInfo
            );

            if(userAccountResponse.isEmpty()) {
                throw new BadCredentialsException("Bad Credentials");
            }

            return ResponseEntity.ok().body(userAccountResponse);
        } catch (BadCredentialsException | UnAuthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}


