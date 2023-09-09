package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.Groups;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddGroupsRequest {

    private String groupName;
    private String description;
    private String manager;
    private int capacity;
    private List<String> groupStack = new ArrayList<>();
    private List<String> member = new ArrayList<>();
    @Builder
    public AddGroupsRequest(String groupName, String description, String manager, int capacity, List<String> groupStack, List<String> member){
        this.groupName = groupName;
        this.description = description;
        this.manager = manager;
        this.capacity = capacity;
        this.groupStack = groupStack;
        this.member = member;
    }

    public Groups toEntity(){
        return Groups.builder()
                .groupName(groupName)
                .description(description)
                .manager(manager)
                .capacity(capacity)
                .groupStack(groupStack)
                .member(member)
                .build();
    }
}
