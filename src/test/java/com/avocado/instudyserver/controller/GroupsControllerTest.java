package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.config.error.exception.GroupsNotFoundException;
import com.avocado.instudyserver.config.error.exception.NotFoundException;
import com.avocado.instudyserver.config.error.exception.UsersNotFoundException;
import com.avocado.instudyserver.domain.Groups;
import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddGroupsRequest;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.repository.GroupsRepository;
import com.avocado.instudyserver.repository.UsersRepository;
import com.avocado.instudyserver.service.GroupsService;
import com.avocado.instudyserver.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.Column;
import javax.persistence.Table;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class GroupsControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    GroupsService groupsService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Transactional
    @DisplayName("createGroups : 그룹 생성에 성공한다")
    @Test
    public void createGroups() throws Exception{
        // 1) given
        final String url = "/groups/create";
        final String groupName = "공사모";
        final String description = "공사모";
        final String manager = "가렌";
        final int capacity = 8;
        final List<String> groupStack = new ArrayList<>(Arrays.asList("스프링"));
        final List<String> member = new ArrayList<>(Arrays.asList("유미"));
        final AddGroupsRequest groupsRequest = new AddGroupsRequest(groupName, description, manager, capacity, groupStack, member);

        // 객체 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(groupsRequest);

        // 2) when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());
        Groups groups = groupsRepository.findByGroupName(groupName).orElseThrow(GroupsNotFoundException::new);
        assertThat(groups.getGroupName()).isEqualTo(groupName);
        assertThat(groups.getDescription()).isEqualTo(description);
        assertThat(groups.getManager()).isEqualTo(manager);
        assertThat(groups.getCapacity()).isEqualTo(capacity);

        // Compare groupStack and member lists
        assertIterableEquals(groups.getGroupStack(), groupStack);
        // 맴버에 추가한 후 비교해야 한다 (실제로 변경됨)
        member.add(manager);
        assertIterableEquals(groups.getMember(), member);
    }

    @Transactional
    @DisplayName("findMyGroups : 유저가 가입한 그룹을 조회합니다.")
    @Test
    public void findMyGroups() throws Exception {
        // 1. given
        final Long userId = 1L;
        final String loginId = "loginId";
        final String password = "password";
        final String name = "name";
        final String email = "email";
        List<String> grouplist = new ArrayList<>();

        usersRepository.save(Users.builder()
                .userId(userId)
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .group(grouplist) // Initialize the group list
                .build());

        Users users = usersRepository.findById(userId).orElseThrow(UsersNotFoundException::new);

        final String url = "/groups/my/{userId}";
        final Long groupId = 1L;
        final String groupName = "공사모";
        final String description = "공사모";
        final String manager = "가렌";
        final int capacity = 8;
        List<String> groupStack = new ArrayList<>(Arrays.asList("스프링"));
        List<String> member = new ArrayList<>(Arrays.asList("유미"));

        groupsRepository.save(Groups.builder()
                .groupId(groupId)
                .groupName(groupName)
                .description(description)
                .manager(manager)
                .capacity(capacity)
                .groupStack(groupStack)
                .member(member)
                .build());

        Groups groups = groupsRepository.findByGroupName(groupName).orElseThrow(NotFoundException::new);

        usersService.joinGroup(userId, groupId);

        // 2. when
        final ResultActions resultActions = mockMvc.perform(get(url, userId));

        // 3. then
        resultActions
                .andExpect(status().isOk());
        // Verify that the user's group list contains the joined group name
        Users updatedUser = usersRepository.findById(userId).orElseThrow(UsersNotFoundException::new);
        assertTrue(updatedUser.getGroup().contains(groupName));
    }
}