package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.config.error.ErrorCode;
import com.avocado.instudyserver.config.error.exception.UsersNotFoundException;
import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.repository.UsersRepository;
import com.avocado.instudyserver.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("signup : 유저 가입에 성공한다.")
    @Test
    public void signup() throws Exception{
        // 1. given
        final String url = "/users/signup";
        final String loginId = "loginId";
        final String password = "password";
        final String name = "name";
        final String email = "email";
        final AddUsersRequest usersRequest = new AddUsersRequest(loginId,password,name,email);

        // 객체 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(usersRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        Users users = usersRepository.findByName(name).orElseThrow(UsersNotFoundException::new);
        assertThat(users.getLoginId()).isEqualTo(loginId);
        assertThat(users.getPassword()).isEqualTo(password);
        assertThat(users.getName()).isEqualTo(name);
        assertThat(users.getEmail()).isEqualTo(email);
    }


    @DisplayName("profile : 유저 아이디로 유저 프로필 정보 가져오기.")
    @Test
    public void profile() throws Exception{
        // 1. given
        final String url = "/users/profile/1";
        final Long userId = 1L;
        final String loginId = "loginId";
        final String password = "password";
        final String name = "name";
        final String email = "email";

        usersRepository.save(Users.builder()
                .userId(userId)
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .build());

        // 2. when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // 3. then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value(loginId))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email));
    }


    @DisplayName("profile : 존재하지 않는 회원을 조회하려고 하면 조회에 실패한다")
    @Test
    public void findUsersInvalidUsers() throws Exception {
        // given
        final String url = "/users/profile/{userId}";
        final long invalidId = 10;

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, invalidId));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorCode.USERS_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.code").value(ErrorCode.USERS_NOT_FOUND.getCode()));
    }
}