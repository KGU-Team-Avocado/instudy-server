package com.avocado.instudyserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoid", updatable = false)
    private Long todoId;

    @Column(name = "todotext", nullable = false)
    private String todoText;

    @Column(name = "studystatus")
    private String studyStatus;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonBackReference
    private Users users;

    // 투두 - 유저 편의메서드 생성
    public void setUsers(Users users) {
        if(this.users != null) {
            this.users.getTodos().remove(this);
        }
        this.users = users;
        users.getTodos().add(this);
    }

}
