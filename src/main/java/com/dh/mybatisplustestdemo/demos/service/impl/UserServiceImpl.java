package com.dh.mybatisplustestdemo.demos.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dh.mybatisplustestdemo.demos.domain.dto.PageDTO;
import com.dh.mybatisplustestdemo.demos.domain.po.Address;
import com.dh.mybatisplustestdemo.demos.domain.vo.AddressVO;
import com.dh.mybatisplustestdemo.demos.domain.vo.UserVO;
import com.dh.mybatisplustestdemo.demos.enumeration.UserStatus;
import com.dh.mybatisplustestdemo.demos.mapper.UserMapper;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.query.UserQuery;
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

        if (user == null || user.getStatus() == UserStatus.FREEZE){
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
        if (user == null || user.getStatus() == UserStatus.FREEZE){
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

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {

        //构建分页条件
/*        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        //排序条件
        if (StrUtil.isNotBlank(query.getSortBy())){
            page.addOrder(query.getIsAsc() ? OrderItem.asc(query.getSortBy()) :
                    OrderItem.desc(query.getSortBy()));
        }else {
            page.addOrder(OrderItem.desc("update_time"));
        }*/

        Page<User> page = query.toMybatisPlusPage();

        String name = query.getUsername();
        Integer status = query.getStatus();
        Integer min = query.getMinBalance();
        Integer max = query.getMaxBalance();

        Page<User> p = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(min != null, User::getBalance, min)
                .le(max != null, User::getBalance, max)
                .page(page);

        //封装VO
/*        PageDTO<UserVO> dto = new PageDTO<>();
        dto.setTotal(p.getTotal());
        dto.setPages(p.getPages());
        List<User> records = p.getRecords();
        if (CollUtil.isEmpty(records)){
            dto.setList(Collections.emptyList());
            return dto;
        }
        dto.setList(BeanUtil.copyToList(records, UserVO.class));
        return dto;*/
//        return PageDTO.of(p, UserVO.class);
        return PageDTO.of(p, user -> {
            //自己写的转换逻辑，可以做脱敏处理
           UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
           vo.setUsername(user.getUsername().substring(0, user.getUsername().length() - 2) + "**");
           return  vo;
        });
    }

    @Override
    public List<User> queryUsers(String username, Integer status, Integer minBalance, Integer maxBalance) {

        return lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }
}
