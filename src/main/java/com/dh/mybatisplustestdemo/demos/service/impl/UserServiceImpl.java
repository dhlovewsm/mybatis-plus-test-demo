package com.dh.mybatisplustestdemo.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mybatisplustestdemo.demos.mapper.UserMapper;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public void deductMoneyById(Long id, Integer money) {
        User user = getById(id);

        if (user == null || user.getStatus() == 2){
            throw new RuntimeException("用户状态异常");
        }
        if (user.getBalance() < money){
            throw new RuntimeException("用户余额不足");
        }
        baseMapper.deductBalance(id, money);
    }
}
