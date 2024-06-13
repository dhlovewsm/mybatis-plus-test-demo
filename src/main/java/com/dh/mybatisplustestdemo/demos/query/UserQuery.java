package com.dh.mybatisplustestdemo.demos.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户查询条件实体")
public class UserQuery extends PageQuery{

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户状态：1-正常 2-冻结")
    private Integer status;

    @ApiModelProperty("最小余额")
    private Integer minBalance;

    @ApiModelProperty("最大余额")
    private Integer maxBalance;

}
