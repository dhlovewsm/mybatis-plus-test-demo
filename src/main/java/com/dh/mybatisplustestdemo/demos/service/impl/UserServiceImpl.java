package com.dh.mybatisplustestdemo.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mybatisplustestdemo.demos.mapper.UserMapper;
import com.dh.mybatisplustestdemo.demos.pojo.User;
import com.dh.mybatisplustestdemo.demos.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
