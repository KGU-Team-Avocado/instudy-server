package com.avocado.instudyserver.domain;

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
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid", updatable = false)
    private Long groupId;

    @Column(name = "groupname", nullable = false)
    private String groupName;

    @Column(name = "description")
    private String description;

    @Column(name = "manager")
    private String manager;

    @Column(name = "capacity")
    private int capacity;

    @ElementCollection
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "groupid"))
    @Column(name = "memberlist")
    private List<String> member = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "groupstack", joinColumns = @JoinColumn(name = "groupid"))
    @Column(name = "groupstacklist")
    private List<String> groupStack = new ArrayList<>();

    public Groups(String groupName, String description, String manager, int capacity, List<String> groupStack, List<String> member) {
        this.groupName = groupName;
        this.description = description;
        this.manager = manager;
        this.capacity = capacity;
        this.groupStack = groupStack;
        this.member = member;
    }
}
