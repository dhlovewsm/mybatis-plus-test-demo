package com.dh.mybatisplustestdemo.demos.service;

import com.dh.mybatisplustestdemo.demos.domain.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IAddressServiceTest {


    @Autowired
    private IAddressService addressService;


    @Test
    public void testInsert()
    {
        Address address = new Address();
        address.setUserId(1L);
        address.setCity("北京");
        address.setMobile("123456789");
        address.setStreet("北京");
        address.setContact("张三");
        address.setNotes("备注");
        address.setProvince("北京");
        address.setTown("北京");
        address.setIsDefault(true);
        address.setDeleted(false);
        addressService.save(address);
    }

    @Test
    public void testQuery()
    {
        Address address = addressService.getById(1L);
        System.out.println(address);
    }

    @Test
    public void testQueryBatch()
    {
        addressService.listByIds(java.util.Arrays.asList(1L, 2L));
    }

    @Test
    public void testUpdate()
    {
        Address address = new Address();
        address.setId(1L);
        address.setUserId(1L);
        address.setCity("北京");
        address.setMobile("123456789");
        address.setContact("张三");
        address.setNotes("备注");
        address.setProvince("北京");
        address.setTown("北京");
        address.setIsDefault(true);
        address.setDeleted(false);
    }

    @Test
    public void testLogicDelete()
    {
        addressService.removeById(1L);

        Address address = addressService.getById(1L);
        System.out.println(address);
    }



}