package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.Users;
import lombok.Getter;

@Getter
public class UsersResponse {

    private final String loginId;
    private final String password;
    private final String name;
    private final String email;

    public UsersResponse(Users users){
        this.loginId = users.getLoginId();
        this.password = users.getPassword();
        this.name = users.getName();
        this.email = users.getEmail();
    }
}
