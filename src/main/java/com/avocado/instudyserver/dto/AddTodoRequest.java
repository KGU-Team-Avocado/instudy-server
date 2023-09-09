package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.StudyStatus;
import com.avocado.instudyserver.domain.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTodoRequest {

    private String todoText;
    @Builder
    public AddTodoRequest(String todoText){
        this.todoText = todoText;
    }

    // 엔티티로 변환시 기본 상태는 READY 이다
    public Todo toEntity(){
        return Todo.builder()
                .todoText(todoText)
                .studyStatus("READY")
                .build();
    }

}
