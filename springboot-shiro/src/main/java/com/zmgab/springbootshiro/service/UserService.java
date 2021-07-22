package com.zmgab.springbootshiro.service;

import com.zmgab.springbootshiro.entity.User;

public interface UserService {

    //注册用户方法
    void register(User user);

    User findByName(String username);

}
