package com.hqyj.onePlusPlus.modules.order.service;


import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;

public interface OrderGroupService {
    //查询：分页、模糊、排序
    PageInfo<OrderGroup> getOrderGroupBySearchVo(SearchVo searchVo);

    //修改：确认收货，修改订单状态
    Result<OrderGroup> updateState(String orderCode);

    //删除：通过订单编号删除
    Result<OrderGroup> deleteOrderGroupByOrderCode(String orderCode);

    //查询：分页、模糊、排序（管理员）
    PageInfo<OrderGroup> getAllOrders(SearchVo searchVo);
}
