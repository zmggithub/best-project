package com.zmgab.springbootshiro.service;

import com.zmgab.springbootshiro.entity.User;

public interface UserService {

    //注册用户方法
    abstract void register(User user);

}
