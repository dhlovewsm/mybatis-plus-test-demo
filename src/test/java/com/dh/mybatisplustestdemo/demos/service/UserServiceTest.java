package com.dh.mybatisplustestdemo.demos.service;

import com.dh.mybatisplustestdemo.demos.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserServiceTest {


    @Autowired
    private UserService userService;


    @Test
    public void testSaveUser(){

        User user = new User();
        user.setName("LiLei");
        user.setPassword("123");
        user.setPhone("18833334444");
        user.setBalance(200);
        user.setInfo(null);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);


    }

    @Test
    public void testQuery(){

        List<User> users = userService.listByIds(List.of(1L, 2L, 4L));
        users.forEach(System.out::println);

    }


}