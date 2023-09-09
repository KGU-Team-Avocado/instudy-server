package com.avocado.instudyserver.service;

import com.avocado.instudyserver.config.error.exception.NotFoundException;
import com.avocado.instudyserver.config.error.exception.UsersNotFoundException;
import com.avocado.instudyserver.domain.StudyStatus;
import com.avocado.instudyserver.domain.Todo;
import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddTodoRequest;
import com.avocado.instudyserver.dto.TodoResponse;
import com.avocado.instudyserver.repository.TodoRepository;
import com.avocado.instudyserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;
    
    // 1. 투두 등록
    public TodoResponse save(Long userId, AddTodoRequest request) {
        Todo todo = request.toEntity();
        Users users = usersRepository.findById(userId).orElseThrow(UsersNotFoundException::new);
        todo.setUsers(users); // 유저와 연관관계
        Todo save = todoRepository.save(todo);
        return new TodoResponse(save);
    }

    // 2. 내 투두 불러오기
    public List<TodoResponse> findTodo(Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow(UsersNotFoundException::new);
        List<Todo> todos = users.getTodos();
        return todos.stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    // 3. 상태 ready -> study로 변경하기
    public TodoResponse modifyStudy(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(NotFoundException::new);
        todo.setStudyStatus("STUDY");
        Todo save = todoRepository.save(todo);
        return new TodoResponse(save);
    }

    // 4. 상태 study -> finish로 변경하기
    public TodoResponse modifyFinish(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(NotFoundException::new);
        todo.setStudyStatus("FINISH");
        Todo save = todoRepository.save(todo);
        return new TodoResponse(save);
    }

    // 5. Todo할일명 변경하기
    public TodoResponse modifyText(Long todoId, AddTodoRequest request) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(NotFoundException::new);
        todo.setTodoText(request.getTodoText());
        Todo save = todoRepository.save(todo);
        return new TodoResponse(save);
    }

    // 6. Todo 삭제하기
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
