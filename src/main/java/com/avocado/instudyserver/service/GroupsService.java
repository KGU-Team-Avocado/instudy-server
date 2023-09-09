package com.avocado.instudyserver.service;

import com.avocado.instudyserver.config.error.exception.GroupsNotFoundException;
import com.avocado.instudyserver.config.error.exception.NotFoundException;
import com.avocado.instudyserver.config.error.exception.UsersNotFoundException;
import com.avocado.instudyserver.domain.Groups;
import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddGroupsRequest;
import com.avocado.instudyserver.dto.GroupsResponse;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.repository.GroupsRepository;
import com.avocado.instudyserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroupsService {

    private final GroupsRepository groupsRepository;
    private final UsersRepository usersRepository;
    public GroupsResponse save(AddGroupsRequest request) {
        Groups entity = request.toEntity();
        entity.getMember().add(entity.getManager());
        Groups groups = groupsRepository.save(entity);
        return new GroupsResponse(groups);
    }

    public List<GroupsResponse> findAllGroups() {
        return groupsRepository.findAll()
                .stream()
                .map(GroupsResponse::new)
                .collect(Collectors.toList());
    }

    public List<GroupsResponse> findMyGroups(Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow(GroupsNotFoundException::new);
        return groupsRepository.findAll()
                .stream()
                .filter(t -> t.getMember().contains(users.getName()))
                .map(GroupsResponse::new)
                .collect(Collectors.toList());
    }

    public GroupsResponse findGroupsName(String groupName) {
        Groups groups = groupsRepository.findByGroupName(groupName).orElseThrow(GroupsNotFoundException::new);
        return new GroupsResponse(groups);
    }

    public GroupsResponse modify(Long groupId, AddGroupsRequest request) {

        Groups groups = groupsRepository.findById(groupId).orElseThrow(GroupsNotFoundException::new);

        if(request.getGroupName() != null){
            groups.setGroupName(request.getGroupName());
        }
        if(request.getDescription() != null){
            groups.setDescription(request.getDescription());
        }
        if(request.getManager() != null){
            groups.setManager(request.getManager());
        }
        if(request.getCapacity() != 0){
            groups.setCapacity(request.getCapacity());
        }
        if(request.getGroupStack() != null){
            groups.setGroupStack(request.getGroupStack());
        }
        Groups modify = groupsRepository.save(groups);
        return new GroupsResponse(modify);
    }

    // 그룹 삭제하기
    public void deleteGroups(Long groupId) {
        groupsRepository.deleteById(groupId);
    }
}
