package com.zmgab.service.impl;

import com.zmgab.gmall.bean.UserAddress;
import com.zmgab.gmall.service.OrderService;
import com.zmgab.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    public List<UserAddress> initOrder(String userId) {
        System.out.println("userId ; " + userId);
        // 查询用户的收货地址
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        userAddressList.forEach(System.out::println);
        return userAddressList;
    }
}
