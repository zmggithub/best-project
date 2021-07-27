package com.zmgab.gmall.service;


import com.zmgab.gmall.bean.UserAddress;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService {

    List<UserAddress> getUserAddressList(String userId);

}
