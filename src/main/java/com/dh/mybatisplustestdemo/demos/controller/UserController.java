package com.dh.mybatisplustestdemo.demos.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dh.mybatisplustestdemo.demos.domain.dto.PageDTO;
import com.dh.mybatisplustestdemo.demos.domain.dto.UserFormDTO;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import com.dh.mybatisplustestdemo.demos.domain.vo.UserVO;
import com.dh.mybatisplustestdemo.demos.query.UserQuery;
import com.dh.mybatisplustestdemo.demos.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@Api(tags = "用户管理接口")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(UserFormDTO userDTO){

        System.out.println(userDTO.toString());
        User user = BeanUtil.copyProperties(userDTO, User.class);
        System.out.println(user);
        userService.save(user);

    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUser(@ApiParam("用户ID") @PathVariable("id") Long id){
        userService.removeById(id);

    }

    @ApiOperation("根据id查询用户接口")
    @GetMapping("/{id}")
    public UserVO queryUserById(@ApiParam("用户ID") @PathVariable("id") Long id){

        return userService.queryUserAndAddressById(id);

    }

    @ApiOperation("根据ids查询用户接口")
    @GetMapping
    public List<UserVO> queryUserByIds(@ApiParam("用户ID") @RequestParam("ids") List<Long> ids){

//        List<User> users = userService.listByIds(ids);
//        return BeanUtil.copyToList(users, UserVO.class);
        return userService.queryUserAndAddressByIds(ids);

    }

    @ApiOperation("扣减用户余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public void deductMoneyById(@ApiParam("用户ID") @PathVariable("id") Long id,
                                   @ApiParam("扣减的金额") @PathVariable("money") Integer money){

        userService.deductMoneyById(id, money);

    }

    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery query){

        List<User> users = userService.queryUsers(
                query.getUsername(), query.getStatus(),
                query.getMinBalance(), query.getMaxBalance());

        return BeanUtil.copyToList(users, UserVO.class);
    }


    @ApiOperation("根据复杂条件分页查询用户接口")
    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query){

        System.out.println(query);
        return userService.queryUsersPage(query);
    }


}
