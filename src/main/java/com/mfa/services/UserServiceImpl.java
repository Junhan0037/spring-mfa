package com.mfa.services;

import com.mfa.datas.entities.UserEntity;
import com.mfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUser(String username) {
        return getUser(UserEntity.builder().username(username).build());
    }

    @Override
    public UserEntity getUser(UserEntity userEntity) {
        return userRepository.findByUsername(userEntity.getUsername());
    }

}
