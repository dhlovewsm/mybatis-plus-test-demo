package com.dh.mybatisplustestdemo.demos.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mybatisplustestdemo.demos.domain.po.Address;
import com.dh.mybatisplustestdemo.demos.domain.vo.AddressVO;
import com.dh.mybatisplustestdemo.demos.domain.vo.UserVO;
import com.dh.mybatisplustestdemo.demos.mapper.UserMapper;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.service.UserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.toolkit.Db;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        /*
         * 1.查询用户信息
         * 2.查询用户地址信息
         * 3.封装数据
         * */
        User user = getById(id);
        if (user == null || user.getStatus() == 2){
            throw new RuntimeException("用户状态异常！");
        }

        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        if (CollUtil.isNotEmpty(addresses)){
            userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        }
        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {

        /*
         * 1.查询用户信息
         * 2.查询用户地址信息
         * 3.封装数据
         * */

        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)){
            return Collections.emptyList();
        }

        List<Integer> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, userIds).list();
        List<AddressVO> addressVOList = BeanUtil.copyToList(addresses, AddressVO.class);

        Map<Long, List<AddressVO>> addressMap = new HashMap<>();
        if (CollUtil.isNotEmpty(addressVOList)){
            addressMap = addressVOList.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }

        List<UserVO> list = new ArrayList<>(users.size());

        for (User user : users) {
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            list.add(vo);
            vo.setAddresses(addressMap.get(vo.getId()));
        }

        return list;
    }
}
