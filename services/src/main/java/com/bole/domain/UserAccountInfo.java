package com.bole.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "username" })
@JsonRootName(value = "accountInfo")
public class UserAccountInfo {
    private String accountNumber;
    private String username;

    public UserAccountInfo(String accountNumber, String username){
        this.accountNumber=accountNumber;
        this.username=username;
    }
}
