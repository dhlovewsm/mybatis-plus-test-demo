package com.dh.mybatisplustestdemo.demos.service.impl;

import com.dh.mybatisplustestdemo.demos.domain.po.Address;
import com.dh.mybatisplustestdemo.demos.mapper.AddressMapper;
import com.dh.mybatisplustestdemo.demos.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DongHao
 * @since 2024-06-12
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
