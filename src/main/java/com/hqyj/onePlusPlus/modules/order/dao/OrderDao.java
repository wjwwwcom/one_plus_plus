package com.hqyj.onePlusPlus.modules.order.dao;

import com.hqyj.onePlusPlus.modules.order.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:26
 * version 1.0
 */
@Repository
@Mapper
public interface OrderDao {
    @Select("select * from `order` where customer_id =#{customerId} and order_state =0")
    List<Order> FindOrderByMyCart(Long customerId);

    @Delete("delete from `order` where order_id =#{orderId}")
    Boolean DeleteCartByOrderId(int orderId);

    @Update("update `order` set order_number=#{orderNumber},order_state=1," +
            "number=#{number},buy_time=#{buyTime},total_price=#{totalPrice}," +
            "address=#{address},phone=#{phone} "+
            "where order_id =#{orderId}")
    Boolean UpdateOrderByOrderId(Order order);

    @Select("select * from `order` where order_id=#{orderId}")
    Order FindOrderByOrderId(int orderId);

     @Insert("insert into `order`(order_number,customer_id,order_state,parameter_id," +
             "goods_id,number,buy_time,total_price,address,phone) values(#{orderNumber}," +
             "#{customerId},#{orderState},#{parameterId},#{goodsId},#{number},#{buyTime}," +
             "#{totalPrice},#{address},#{phone})")
    Boolean InsertOrder(Order order);
    //订单开始（王福）
    // 查询：通过订单编号获取Order对象
    @Select("select * from `order` where order_number = #{orderNumber}")
    List<Order> getOrders(String orderNumber);

    @Update("update `order` set order_state = 2 where order_number = #{orderNumber}")
    void updateState(String orderNumber);
    //订单结束


    //=================================================

    @Select("select * from `order`where customer_id = #{customerId}")
    List<Order> getHistoryOrders(BigInteger customerId);
    //=====================================================
}
