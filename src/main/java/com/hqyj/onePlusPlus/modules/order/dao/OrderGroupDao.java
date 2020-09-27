package com.hqyj.onePlusPlus.modules.order.dao;


import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alen
 * @createTime 2020-09-17-15:56
 * @Decription:
 **/

@Repository
@Mapper
public interface OrderGroupDao {

    @Insert("insert into order_group(order_group_name,order_code," +
            "order_total_price,order_time,order_state,order_is_delete,order_customer_id) " +
            "values(#{orderGroupName},#{orderCode},#{orderTotalPrice}," +
            "#{orderTime},#{orderState},#{orderIsDelete},#{orderCustomerId})")
    Boolean InsertOrderGroup(OrderGroup orderGroup);

    //订单开始（王福）
    @Select("<script>" +
            "select * from `order_group` "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (order_code like '%${keyWord}%') "
            + " or (convert(order_time,DATETIME) like '%${keyWord}%') "
            + " or (order_total_price like '%${keyWord}%') "
            + " or (order_group_name like '%${keyWord}%') "
            + " or (order_state like '%${keyWord}%') "
            + "</if>"
            + " and order_is_delete=${1} and order_customer_id = #{orderCustomerId}"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by order_time desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<OrderGroup> getOrderGroups(SearchVo searchVo);

    @Update("update order_group set order_state = 2 where order_code = #{orderCode}")
    void updateState(String orderCode);

    @Update("update order_group set order_is_delete = 0 where order_code = #{orderCode}")
    void deleteOrderGroupByOrderCode(String orderCode);
    //订单结束

    //===============================================
    @Select("<script>" +
            "select * from `order_group` "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (order_code like '%${keyWord}%') "
            + " or (convert(order_time,DATETIME) like '%${keyWord}%') "
            + " or (order_total_price like '%${keyWord}%') "
            + " or (order_group_name like '%${keyWord}%') "
            + " or (order_state like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by order_time desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<OrderGroup> getAllOrders(SearchVo searchVo);
}
