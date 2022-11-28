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
@JsonRootName(value = "userInfo")
public class UserInfo {

    private String name;
    private String surname;
    private String sex;
    private String age;
    private String username;

    public UserInfo(String name, String surname, String sex, String age, String username) {
        setName(name);
        setSurname(surname);
        setSex(sex);
        setAge(age);
        setUsername(username);
    }
}
