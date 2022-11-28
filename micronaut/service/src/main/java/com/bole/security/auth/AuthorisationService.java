package com.bole.security.auth;

import com.bole.exception.BadRequestException;
import com.bole.exception.UnAuthorizedException;

public interface AuthorisationService {

    String obtainAuthToken(String username, String password) throws BadRequestException, UnAuthorizedException;
}
