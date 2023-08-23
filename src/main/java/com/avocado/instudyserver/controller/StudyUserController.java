package com.avocado.instudyserver.controller;

import com.avocado.instudyserver.domain.StudyUser;
import com.avocado.instudyserver.repository.StudyUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class StudyUserController {

    private final StudyUserRepository studyUserRepository;

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원 목록을 조회합니다.")
    @GetMapping("/users")
    public List<StudyUser> findAllUser() {
        return studyUserRepository.findAll();
    }

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public StudyUser save(@ApiParam(value = "회원 아이디", required = true) @RequestParam String id,
                          @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
                          @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                          @ApiParam(value = "회원 이메일", required = true) @RequestParam String email) {
        StudyUser user = StudyUser.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .build();

        return studyUserRepository.save(user);
    }

    @ApiOperation(value = "회원 검색 (이름)", notes = "이름으로 회원을 검색합니다.")
    @GetMapping("/findUserByName/{name}")
    public List<StudyUser> findUserByName(@ApiParam(value = "회원 이름", required = true) @PathVariable String name) {
        return studyUserRepository.findByName(name);
    }

    @ApiOperation(value = "회원 검색 (이메일)", notes = "이메일로 회원을 검색합니다.")
    @GetMapping("/findUserByEmail/{email}")
    public StudyUser findUserByEmail(@ApiParam(value = "회원 이메일", required = true) @PathVariable String email) {
        return studyUserRepository.findByEmail(email);
    }
}
