package com.crv.usersgroup.core.security;

import com.crv.usersgroup.core.security.entity.UserEntity;
import com.crv.usersgroup.domain.repository.UserRepository;
import com.crv.usersgroup.domain.repository.UsersGroupRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UsersGroupRepository usersGroupRepository;

    public CustomUserDetailService(UserRepository userRepository, UsersGroupRepository usersGroupRepository) {
        this.userRepository = userRepository;
        this.usersGroupRepository = usersGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        entity.setUsersGroups(usersGroupRepository.findUsersGroupAndPermissionsByUserId(entity.getId()));

        return entity;

    }

}
