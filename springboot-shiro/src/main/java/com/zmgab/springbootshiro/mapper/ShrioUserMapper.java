package com.zmgab.springbootshiro.mapper;

import com.zmgab.springbootshiro.entity.Perms;
import com.zmgab.springbootshiro.entity.Role;
import com.zmgab.springbootshiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShrioUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    User findRolesByUserName(String username);

    List<Perms> findPermsByRoleId(String id);
}