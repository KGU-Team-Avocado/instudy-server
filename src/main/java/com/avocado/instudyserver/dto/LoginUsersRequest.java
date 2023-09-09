package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUsersRequest {

    private String loginId;
    private String password;

    @Builder
    public LoginUsersRequest(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }

    public Users toEntity(){
        return Users.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}
