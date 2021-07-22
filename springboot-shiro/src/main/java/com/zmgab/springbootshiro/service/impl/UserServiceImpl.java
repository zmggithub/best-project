package com.zmgab.springbootshiro.service.impl;

import com.zmgab.springbootshiro.entity.User;
import com.zmgab.springbootshiro.mapper.ShrioUserMapper;
import com.zmgab.springbootshiro.service.UserService;
import com.zmgab.springbootshiro.util.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    ShrioUserMapper mapper;

    @Override
    public void register(User user) {
        // 处理业务调用dao
        // 1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 2.将随机盐保存到数据
        user.setSalt(salt);
        // 3.明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        mapper.insert(user);
    }

    @Override
    public User findByName(String username) {
        return mapper.selectByUsername(username);
    }
}
