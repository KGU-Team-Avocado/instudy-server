package com.avocado.instudyserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", updatable = false)
    private Long userId;

    @Column(name = "loginid", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "joingroup", joinColumns = @JoinColumn(name = "userid"))
    @Column(name = "joingrouplist")
    private List<String> group = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    @JsonManagedReference
    private List<Todo> todos = new ArrayList<>();

}
