package com.avocado.instudyserver.repository;

import com.avocado.instudyserver.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByLoginId(String loginId);
    Optional<Users> findByName(String name);
    Optional<Users> findByEmail(String email);

}
