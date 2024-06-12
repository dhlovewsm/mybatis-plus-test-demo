package com.dh.mybatisplustestdemo.demos.service;

import com.dh.mybatisplustestdemo.demos.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceTest {


    @Autowired
    private UserService userService;


    @Test
    public void testSaveUser(){

        User user = new User();
        user.setUsername("LiLei");
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

    private User builderUser(int i){
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (18899998888L + i));
        user.setBalance(2000);
        user.setInfo("{\"age\": 24}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        return user;
    }


    /**
     * for循环插入10W条数据
     * 耗时：78428ms
     */
    @Test
    public void testSaveOneByOnce(){

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i ++){
            userService.save(builderUser(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));

    }

    /**
     * 批量插入10w条数据
     * 每次插入1000条，插入100次
     * 没加配置之前耗时：13596ms
     * 加了配置之后耗时：4282ms
     * 注意：需要在application.yml中配置rewriteBatchedStatements=true才能真正的批处理
     */
    @Test
    public void testSaveBatches(){

        List<User> users = new ArrayList<>(1000);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i ++){
            users.add(builderUser(i));
            if (i % 1000 == 0){
                userService.saveBatch(users);
                users.clear();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" +(end - start));

    }


}