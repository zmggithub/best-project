package com.zmgab.service.impl;


import com.zmgab.gmall.bean.UserAddress;
import com.zmgab.gmall.service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {

    public List<UserAddress> getUserAddressList(String userId) {

        UserAddress li = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层。", "1", "李老师", "13655665588", "1");
        UserAddress wang = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "2", "王老师", "13655665588", "1");

        return Arrays.asList(li,wang);
    }
























}
