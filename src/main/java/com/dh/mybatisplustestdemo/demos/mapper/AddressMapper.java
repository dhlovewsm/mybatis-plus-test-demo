package com.dh.mybatisplustestdemo.demos.mapper;

import com.dh.mybatisplustestdemo.demos.domain.po.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DongHao
 * @since 2024-06-12
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}
