package com.consumer.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.consumer.dao.UserDao;
import com.consumer.entity.UserEntity;
import com.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Fox
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    //@SentinelResource(value = "getUser")
    @SentinelResource(value = "getUser",blockHandler = "handleException")
    public UserEntity getById(Integer id) {

        return userDao.getById(id);
    }

    public UserEntity handleException(Integer id, BlockException ex) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("===被限流降级啦===");
        return userEntity;
    }
}
