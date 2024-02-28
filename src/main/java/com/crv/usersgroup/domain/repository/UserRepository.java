package com.crv.usersgroup.domain.repository;

import com.crv.usersgroup.core.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("FROM UserEntity WHERE username = :username")
    Optional<UserEntity> findByUsername(String username);

}
