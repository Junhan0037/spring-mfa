package com.mfa.services;

import com.mfa.datas.entities.UserEntity;

public interface UserService {

    UserEntity getUser(String username);

    UserEntity getUser(UserEntity userEntity);

}
