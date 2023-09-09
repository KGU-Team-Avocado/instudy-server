package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUsersRequest {

    private String loginId;
    private String password;
    private String name;
    private String email;

    @Builder
    public AddUsersRequest(String loginId, String password, String name, String email){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Users toEntity(){
        return Users.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
