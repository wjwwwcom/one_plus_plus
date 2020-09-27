package com.hqyj.onePlusPlus.modules.order.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.dao.OrderDao;
import com.hqyj.onePlusPlus.modules.order.dao.OrderGroupDao;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;
import com.hqyj.onePlusPlus.modules.order.service.OrderGroupService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderGroupServiceImpl implements OrderGroupService {
    @Autowired
    private OrderGroupDao orderGroupDao;

    @Autowired
    private OrderDao orderDao;

    //修改：确认收货，修改订单状态
    @Override
    public Result<OrderGroup> updateState(String orderCode) {
        //修改订单列表的订单状态
        orderGroupDao.updateState(orderCode);
        String orderNumber = orderCode;
        //修改订单中详细商品的状态
        List<Order> orders = orderDao.getOrders(orderNumber);
        orders.stream().forEach(order -> {
            orderDao.updateState(orderNumber);
        });
        return new Result<OrderGroup>(Result.ResultStatus.SUCCESS.status,"update success.");
    }

    //删除：通过订单编号删除
    @Override
    public Result<OrderGroup> deleteOrderGroupByOrderCode(String orderCode) {
        orderGroupDao.deleteOrderGroupByOrderCode(orderCode);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"delete success");
    }

    //查询：分页、模糊、排序
    @Override
    public PageInfo<OrderGroup> getOrderGroupBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute("user");

        searchVo.setOrderCustomerId(user.getUserId());
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<>(Optional.ofNullable(orderGroupDao.getOrderGroups(searchVo))
                                .orElse(Collections.emptyList()));
    }

    //==========================================================
    @Override
    public PageInfo<OrderGroup> getAllOrders(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<>(Optional.ofNullable(orderGroupDao.getAllOrders(searchVo))
                .orElse(Collections.emptyList()));
    }
}
