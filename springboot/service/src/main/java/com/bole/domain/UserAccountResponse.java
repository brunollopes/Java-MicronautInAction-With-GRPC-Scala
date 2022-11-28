package com.bole.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
{
  "accountInfo": {
    "accountNumber": "12345-3346-3335-4456"
  },
  "userInfo": {
    "name": "John",
    "surname": "Doe",
    "sex": "male",
    "age": 32
  }
}
 */

@Getter
@Setter
@AllArgsConstructor
public class UserAccountResponse {
    private UserAccountInfo accountInfo;
    private UserInfo userInfo;

}
