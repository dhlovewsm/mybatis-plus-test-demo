package com.dh.mybatisplustestdemo.demos.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dh.mybatisplustestdemo.demos.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    void updateBalanceByIds(@Param("ew") QueryWrapper<User> wrapper, @Param("amount") int amount);
}
