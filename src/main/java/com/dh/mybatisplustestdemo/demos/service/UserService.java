package com.dh.mybatisplustestdemo.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.mybatisplustestdemo.demos.domain.dto.PageDTO;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.domain.vo.UserVO;
import com.dh.mybatisplustestdemo.demos.query.UserQuery;

import java.util.List;

public interface UserService extends IService<User> {
    void deductMoneyById(Long id, Integer money);

    UserVO queryUserAndAddressById(Long id);

    List<UserVO> queryUserAndAddressByIds(List<Long> ids);

    PageDTO<UserVO> queryUsersPage(UserQuery query);

    List<User> queryUsers(String username, Integer status, Integer minBalance, Integer maxBalance);
}
