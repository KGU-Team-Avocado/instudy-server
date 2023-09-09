package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.domain.Todo;
import com.avocado.instudyserver.dto.AddTodoRequest;
import com.avocado.instudyserver.dto.TodoResponse;
import com.avocado.instudyserver.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Todo"})
@RequiredArgsConstructor
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    @ApiOperation(value = "투두 생성", notes = "투두를 등록합니다.")
    @PostMapping(value = "/todo/create/{userId}", consumes = "application/json")
    public ResponseEntity<TodoResponse> makeTodo(@PathVariable("userId") Long userId, @RequestBody AddTodoRequest request){
        TodoResponse todo = todoService.save(userId, request);
        return ResponseEntity.ok(todo);
    }

    @ApiOperation(value = "내 투두 읽기", notes = "사용자의 투두를 모두 읽습니다")
    @GetMapping(value = "/todo/read/{userId}")
    public ResponseEntity<List<TodoResponse>> findTodo(@PathVariable("userId") Long userId){
        List<TodoResponse> todoList = todoService.findTodo(userId);
        return ResponseEntity.ok(todoList);
    }

    @ApiOperation(value = "투두 STUDY 상태로 바꾸기", notes = "투두의 상태를 READY에서 STUDY로 변경합니다")
    @PatchMapping(value = "/todo/modify/study/{todoId}")
    public ResponseEntity<TodoResponse> modifyStudy(@PathVariable("todoId") Long todoId){
        TodoResponse todo = todoService.modifyStudy(todoId);
        return ResponseEntity.ok(todo);
    }


    @ApiOperation(value = "투두 FINISH 상태로 바꾸기", notes = "투두의 상태를 STUDY에서 FINISH로 변경합니다")
    @PatchMapping(value = "/todo/modify/finish/{todoId}")
    public ResponseEntity<TodoResponse> modifyFinish(@PathVariable("todoId") Long todoId){
        TodoResponse todo = todoService.modifyFinish(todoId);
        return ResponseEntity.ok(todo);
    }


    @ApiOperation(value = "투두 내용 변경", notes = "투두의 내용을 변경합니다")
    @PatchMapping(value = "/todo/modify/text/{todoId}", consumes = "application/json")
    public ResponseEntity<TodoResponse> modifyText(@PathVariable("todoId") Long todoId, @RequestBody AddTodoRequest request){
        TodoResponse todo = todoService.modifyText(todoId, request);
        return ResponseEntity.ok(todo);
    }

    @ApiOperation(value = "투두 삭제하기", notes = "투두를 삭제합니다")
    @DeleteMapping(value = "/todo/delete/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todoId") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok()
                .build();
    }

}
