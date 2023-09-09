package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.dto.AddGroupsRequest;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.dto.GroupsResponse;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.service.GroupsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Groups"})
@RequiredArgsConstructor
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class GroupsController {

    private final GroupsService groupsService;

    @ApiOperation(value = "그룹 생성", notes = "그룹을 생성합니다.")
    @PostMapping(value = "/groups/create", consumes = "application/json")
    public ResponseEntity<GroupsResponse> createGroups(@RequestBody AddGroupsRequest request) {
        GroupsResponse groups = groupsService.save(request);
        return ResponseEntity.ok(groups);
    }

    @ApiOperation(value = "모든 그룹 조회", notes = "모든 그룹을 조회합니다.")
    @GetMapping(value = "/groups/all")
    public ResponseEntity<List<GroupsResponse>> findAllGroups() {
        List<GroupsResponse> groups = groupsService.findAllGroups();
        return ResponseEntity.ok(groups);
    }

    @ApiOperation(value = "유저가 가입한 그룹 조회", notes = "유저가 가입한 그룹을 조회합니다.")
    @GetMapping(value = "/groups/my/{userId}")
    public ResponseEntity<List<GroupsResponse>> findMyGroups(@PathVariable("userId") Long userId) {
        List<GroupsResponse> groups = groupsService.findMyGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @ApiOperation(value = "그룹 검색하기", notes = "그룹을 검색합니다.")
    @GetMapping(value = "/groups/search/{groupName}")
    public ResponseEntity<GroupsResponse> findGroupsName(@PathVariable("groupName") String groupName) {
        GroupsResponse groups = groupsService.findGroupsName(groupName);
        return ResponseEntity.ok(groups);
    }

    @ApiOperation(value = "그룹 수정하기", notes = "그룹을 수정합니다.")
    @PatchMapping(value = "/groups/modify/{groupId}", consumes = "application/json")
    public ResponseEntity<GroupsResponse> modifyGroups(@PathVariable("groupId") Long groupId, @RequestBody AddGroupsRequest request) {
        GroupsResponse groups = groupsService.modify(groupId, request);
        return ResponseEntity.ok(groups);
    }

    @ApiOperation(value = "그룹 삭제하기", notes = "그룹을 삭제합니다")
    @DeleteMapping(value = "/groups/delete/{groupId}")
    public ResponseEntity<Void> deleteGroups(@PathVariable("groupId") Long groupId){
        groupsService.deleteGroups(groupId);
        return ResponseEntity.ok()
                .build();
    }
}
