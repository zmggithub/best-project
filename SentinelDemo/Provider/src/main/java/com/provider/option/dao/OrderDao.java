package com.provider.option.dao;

import com.provider.option.entity.OrderEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
@Mapper
public interface OrderDao {

    @Select("select * from t_order where user_id=#{userId}")
    List<OrderEntity> listByUserId(Integer userId);

}
