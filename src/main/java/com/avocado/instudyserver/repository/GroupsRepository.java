package com.avocado.instudyserver.repository;

import com.avocado.instudyserver.domain.Groups;
import com.avocado.instudyserver.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupsRepository extends JpaRepository<Groups, Long> {
    Optional<Groups> findByGroupName(String groupName);
}
