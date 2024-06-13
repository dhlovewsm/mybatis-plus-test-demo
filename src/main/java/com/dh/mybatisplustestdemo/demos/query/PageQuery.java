package com.dh.mybatisplustestdemo.demos.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dh.mybatisplustestdemo.demos.domain.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分页查询实体")
public class PageQuery {

    private static final String SORT_BY_UPDATE_TIME = "update_time";
    private static final String SORT_BY_CREATE_TIME = "create_time";

    @ApiModelProperty("页码")
    private Integer pageNo = 1;

    @ApiModelProperty("每页数量")
    private Integer pageSize = 5;

    @ApiModelProperty("排序字段")
    private String sortBy;

    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;

    public <T>Page<T> toMybatisPlusPage(OrderItem... items){

        Page<T> page = Page.of(pageNo, pageSize);

        if (StrUtil.isNotBlank(sortBy)){
            page.addOrder(isAsc ? OrderItem.asc(sortBy) : OrderItem.desc(sortBy));
        }else if (items != null){
            page.addOrder(items);
        }

        return page;

    }

    public <T> Page<T> toMybatisPlusPageSortByCreateTime(){
        return toMybatisPlusPage(OrderItem.desc(SORT_BY_CREATE_TIME));
    }

    public <T> Page<T> toMybatisPlusPageSortByUpdateTime(){
        return toMybatisPlusPage(OrderItem.desc(SORT_BY_UPDATE_TIME));
    }

    public <T> Page<T> toMybatisPlusPage(String sortBy, Boolean isAsc){
        return toMybatisPlusPage(isAsc ? OrderItem.asc(sortBy) : OrderItem.desc(sortBy));
    }

}
