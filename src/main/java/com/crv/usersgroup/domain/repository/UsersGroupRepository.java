package com.crv.usersgroup.domain.repository;

import com.crv.usersgroup.core.security.entity.UsersGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UsersGroupRepository extends JpaRepository<UsersGroup, Long> {

    Optional<UsersGroup> findByCode(String code);

    @Query("SELECT u.usersGroups FROM UserEntity u WHERE u.id = :userId")
    Set<UsersGroup> findUsersGroupByUserId(Long userId);

    @Query("SELECT DISTINCT ug FROM UserEntity u JOIN u.usersGroups ug " +
            "LEFT JOIN FETCH ug.permissions WHERE u.id = :userId")
    Set<UsersGroup> findUsersGroupAndPermissionsByUserId(@Param("userId") Long userId);

}
