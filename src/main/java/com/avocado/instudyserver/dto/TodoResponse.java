package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.StudyStatus;
import com.avocado.instudyserver.domain.Todo;
import lombok.Getter;

@Getter
public class TodoResponse {

    private String todoText;
    private String studyStatus;
    public TodoResponse(Todo todo){
        this.todoText = todo.getTodoText();
        this.studyStatus = todo.getStudyStatus();
    }
}
