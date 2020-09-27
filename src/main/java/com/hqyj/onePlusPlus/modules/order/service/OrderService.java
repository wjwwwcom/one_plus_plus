package com.hqyj.onePlusPlus.modules.order.service;


import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import com.hqyj.onePlusPlus.modules.order.entity.Order;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:26
 * version 1.0
 */
public interface OrderService {

    Result<List<Order>> FindOrderByMyCart();

    Result<Order> DeleteCartByOrderId(int orderId);

    Result<Order> DelAllCartByOrderId(String doai);

    Result<Order> PurchaseByOrder(String orderstr);
    Boolean StockManage(int orderId, int number);

    Result<User> FindUserByUserId(long userId);

    Order FindOrderByOrderId(int orderId);

    List<GoodsParameter> FindGoodsParByGoodsId(Integer goodsId);

    Goods FindGoodsByGoodsId(Integer goodsId);

    Result<Boolean> PurchaseGoods(Order order);

    Result<Boolean> AddCart(Order order);

    // 分页查询订单数据
    List<Order> getOrders(String orderNumber);

    //根据订单号查询详情
    List<Order> getOneOrderGroup(String orderNumber);
}
