package com.dh.mybatisplustestdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusTestDemoApplicationTests {


    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);

    }

    @Test
    public void testInsert(){

        User user = new User();
        user.setUsername("Jack");
        user.setPassword("123456");

        System.out.println(userMapper.insert(user));

    }


    @Test
    public void testQueryBatch(){

        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);

    }

    /**
     * 查询名字中带o的，存款大于等于1000的用户的id，username, info, balance字段
     */
    @Test
    public void testQueryWrapper(){

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    /**
     * 更新用户名为Jack的用户余额为2000
     */
    @Test
    public void testUpdateByQueryWrapper(){

        User user = new User();
        user.setBalance(2000);

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", "Jack");
        userMapper.update(user, wrapper);

    }

    /**
     * 更新id为1，2，4的用户余额，扣200
     */
    @Test
    public void testUpdateByUpdateWrapper(){

        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", ids);
        userMapper.update(null, wrapper);

    }

    @Test
    public void testLambdaQueryWrapper(){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    public void testCustomSqlUpdate(){

        List<Long> ids = List.of(1L, 2L, 4L);

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("id", ids);

        int amount = 200;
        userMapper.updateBalanceByIds(wrapper, amount);

    }


}
