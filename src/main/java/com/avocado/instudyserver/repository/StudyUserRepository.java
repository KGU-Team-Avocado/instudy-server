package com.avocado.instudyserver.repository;

import com.avocado.instudyserver.domain.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudyUserRepository extends JpaRepository<StudyUser, Long> {

    List<StudyUser> findByName(String name);
    StudyUser findByEmail(String email);
}
