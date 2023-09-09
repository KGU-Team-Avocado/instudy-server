package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.dto.LoginUsersRequest;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.repository.UsersRepository;
import com.avocado.instudyserver.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. Users"})
@RequiredArgsConstructor
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class UsersController {

    private final UsersService usersService;

    @ApiOperation(value = "유저 등록", notes = "유저를 등록합니다.")
    @PostMapping(value = "/users/signup", consumes = "application/json")
    public ResponseEntity<UsersResponse> signup(@RequestBody AddUsersRequest addUsersRequest) {
        UsersResponse users = usersService.save(addUsersRequest);
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "유저 로그인", notes = "유저 로그인 합니다.")
    @PostMapping(value = "/users/signin", consumes = "application/json")
    public ResponseEntity<UsersResponse> signup(@RequestBody LoginUsersRequest loginUsersRequest) {
        UsersResponse users = usersService.login(loginUsersRequest);
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "유저 프로필", notes = "유저 프로필 정보 가져오기.")
    @GetMapping(value = "/users/profile/{userId}")
    public ResponseEntity<UsersResponse> profile(@PathVariable("userId") Long userId) {
        UsersResponse users = usersService.profile(userId);
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "유저 그룹 가입", notes = "유저가 그룹에 가입합니다.")
    @PostMapping(value = "/users/join/{userId}/{groupId}")
    public ResponseEntity<Void> joinGroup(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId) {
        usersService.joinGroup(userId, groupId);
        return ResponseEntity.ok()
                .build();
    }


    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보를 수정합니다.")
    @PatchMapping(value = "/users/modify/{userId}", consumes = "application/json")
    public ResponseEntity<UsersResponse> modifyUser(@PathVariable("userId") Long userId, @RequestBody AddUsersRequest addUsersRequest){
        UsersResponse users = usersService.modify(userId, addUsersRequest);
        return ResponseEntity.ok(users);
    }
    
    // 회원 이미지 수정하기

    @ApiOperation(value = "모든 유저 조회", notes = "모든 유저 목록을 조회합니다.")
    @GetMapping(value = "/users/all")
    public ResponseEntity<List<UsersResponse>> findAllUser() {
        List<UsersResponse> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }


    @ApiOperation(value = "유저 검색 (이름)", notes = "이름으로 유저를 검색합니다.")
    @GetMapping("/users/find/name/{name}")
    public ResponseEntity<UsersResponse> findUserByName(@PathVariable("name") String name) {
        UsersResponse users = usersService.findByName(name);
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "유저 검색 (이메일)", notes = "이메일로 유저를 검색합니다.")
    @GetMapping("/users/find/email/{email}")
    public ResponseEntity<UsersResponse> findUserByEmail(@PathVariable("email") String email) {
        UsersResponse users = usersService.findByEmail(email);
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "유저 삭제", notes = "유저를 삭제합니다")
    @DeleteMapping(value = "/users/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        usersService.delete(userId);
        return ResponseEntity.ok()
                .build();
    }
}
