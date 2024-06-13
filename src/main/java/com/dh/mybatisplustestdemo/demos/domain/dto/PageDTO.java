package com.dh.mybatisplustestdemo.demos.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页数")
    private Long pages;

    @ApiModelProperty("当前页数据")
    private List<T> list;

}
