package com.provider.option.service;

import com.provider.option.entity.OrderEntity;
import java.util.List;

/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
public interface OrderService {


    List<OrderEntity> listByUserId(Integer userId);
}

