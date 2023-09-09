package com.avocado.instudyserver.service;

import com.avocado.instudyserver.config.error.exception.NotFoundException;
import com.avocado.instudyserver.config.error.exception.UsersNotFoundException;
import com.avocado.instudyserver.domain.Groups;
import com.avocado.instudyserver.domain.Users;
import com.avocado.instudyserver.dto.AddUsersRequest;
import com.avocado.instudyserver.dto.LoginUsersRequest;
import com.avocado.instudyserver.dto.UsersResponse;
import com.avocado.instudyserver.repository.GroupsRepository;
import com.avocado.instudyserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final GroupsRepository groupsRepository;

    // 유저 등록
    public UsersResponse save(AddUsersRequest addUsersRequest){
        Users users = usersRepository.save(addUsersRequest.toEntity());
        return new UsersResponse(users);
    }

    // 유저 로그인
    public UsersResponse login(LoginUsersRequest loginUsersRequest) {
        Users users = usersRepository.findByLoginId(loginUsersRequest.getLoginId()).orElseThrow(UsersNotFoundException::new);

        if(users.getPassword().equals(loginUsersRequest.getPassword())){
            return new UsersResponse(users);
        } else {
            throw new UsersNotFoundException();
        }
    }
    
    // 유저 모두 가져오기
    public List<UsersResponse> findAllUsers(){
        return usersRepository.findAll()
                .stream()
                .map(UsersResponse::new)
                .collect(Collectors.toList());
    }

    // 유저 프로필 가져오기
    public UsersResponse profile(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(UsersNotFoundException::new);
        return new UsersResponse(users);
    }

    // 유저 수정하기
    public UsersResponse modify(Long userId, AddUsersRequest addUsersRequest) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(UsersNotFoundException::new);

        if(addUsersRequest.getLoginId() != null){
            users.setLoginId(addUsersRequest.getLoginId());
        }
        if(addUsersRequest.getPassword() != null){
            users.setPassword(addUsersRequest.getPassword());
        }
        if(addUsersRequest.getName() != null){
            users.setName(addUsersRequest.getName());
        }
        if(addUsersRequest.getEmail() != null){
            users.setEmail(addUsersRequest.getEmail());
        }
        Users user = usersRepository.save(users);
        return new UsersResponse(user);
    }

    // 유저 이름으로 찾기
    public UsersResponse findByName(String name) {
        Users users = usersRepository.findByName(name).orElseThrow(UsersNotFoundException::new);
        return new UsersResponse(users);
    }

    // 유저 이메일로 찾기
    public UsersResponse findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow(UsersNotFoundException::new);
        return new UsersResponse(users);
    }

    // 유저 삭제하기
    public void delete(Long userId) {
        usersRepository.deleteById(userId);
    }

    // 유저가 그룹에 가입하기
    public void joinGroup(Long userId, Long groupId) {
        Users users = usersRepository.findById(userId).orElseThrow(UsersNotFoundException::new);
        Groups groups = groupsRepository.findById(groupId).orElseThrow(NotFoundException::new);
        users.getGroup().add(groups.getGroupName());
        groups.getMember().add(users.getName());
        usersRepository.save(users);
        groupsRepository.save(groups);
    }
}
