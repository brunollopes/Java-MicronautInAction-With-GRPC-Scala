package com.bole.security.auth;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
