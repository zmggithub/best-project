package com.provider.option.service.impl;

import com.provider.option.dao.OrderDao;
import com.provider.option.entity.OrderEntity;
import com.provider.option.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderEntity> listByUserId(Integer userId) {
        return orderDao.listByUserId(userId);
    }

}