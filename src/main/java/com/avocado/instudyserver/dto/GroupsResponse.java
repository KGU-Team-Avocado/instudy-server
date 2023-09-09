package com.avocado.instudyserver.dto;

import com.avocado.instudyserver.domain.Groups;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GroupsResponse {

    private String groupName;
    private String description;
    private String manager;
    private int capacity;
    private List<String> member = new ArrayList<>();
    private List<String> groupStack = new ArrayList<>();

    public GroupsResponse(Groups groups){
        this.groupName = groups.getGroupName();
        this.description = groups.getDescription();
        this.manager = groups.getManager();
        this.capacity = groups.getCapacity();
        this.member = groups.getMember();
        this.groupStack = groups.getGroupStack();
    }
}
