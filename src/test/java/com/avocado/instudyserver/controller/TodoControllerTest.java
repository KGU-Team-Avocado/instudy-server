package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.config.error.exception.NotFoundException;
import com.avocado.instudyserver.domain.Todo;
import com.avocado.instudyserver.dto.AddTodoRequest;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.dto.TodoResponse;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.repository.GroupsRepository;
import com.avocado.instudyserver.repository.TodoRepository;
import com.avocado.instudyserver.service.GroupsService;
import com.avocado.instudyserver.service.TodoService;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class TodoControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("makeTodo : 투두 생성에 성공한다.")
    @Test
    public void makeTodo() throws Exception{
        // 1. given
        final String url = "/todo/create/1";
        final Long userId = 1L;
        final String todoText = "투두생성";
        final AddTodoRequest request = new AddTodoRequest(todoText);

        // 객체 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        Todo todo = todoRepository.findByTodoText(todoText).orElseThrow(NotFoundException::new);
        assertThat(todo.getTodoText()).isEqualTo(todoText);
        assertThat(todo.getStudyStatus()).isEqualTo("READY");
    }
}