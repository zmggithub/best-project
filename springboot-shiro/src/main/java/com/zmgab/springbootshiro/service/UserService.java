package com.zmgab.springbootshiro.service;

import com.zmgab.springbootshiro.entity.Perms;
import com.zmgab.springbootshiro.entity.User;

import java.util.List;

public interface UserService {

    //注册用户方法
    void register(User user);

    User findByName(String username);

    User findRolesByUserName(String username);

    List<Perms> findPermsByRoleId(String id);
}
