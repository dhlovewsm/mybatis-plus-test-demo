package com.dh.mybatisplustestdemo.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.domain.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    void deductMoneyById(Long id, Integer money);

    UserVO queryUserAndAddressById(Long id);

    List<UserVO> queryUserAndAddressByIds(List<Long> ids);
}
