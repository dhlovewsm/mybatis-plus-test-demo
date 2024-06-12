package com.dh.mybatisplustestdemo.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageQueryTest {

    @Autowired
    UserService userService;

    @Test
    public void testPageQuery(){

        int pageNo = 1, pageSize = 5;

        Page<User> page = Page.of(pageNo, pageSize);


        page.addOrder(OrderItem.asc("balance"));
        page.addOrder(OrderItem.asc("id"));

        Page<User> p = userService.page(page);

        System.out.println("total = " + p.getTotal());

        System.out.println("pages = " + p.getPages());

        List<User> records = p.getRecords();

        records.forEach(System.out::println);

    }

}
