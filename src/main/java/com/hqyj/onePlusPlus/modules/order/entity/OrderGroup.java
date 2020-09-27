package com.hqyj.onePlusPlus.modules.order.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Alen
 * @createTime 2020-09-17-15:12
 * @Decription:
 **/
public class OrderGroup {

    private int  orderGroupId;//主键订单Id
    private String orderGroupName;//订单的所有物品名称
    private String orderCode;//订单号
    private Double orderTotalPrice;//总金额
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;//订单时间
    private int orderState;//订单状态 ：0已下单，1已发货，2已收货
    private int orderIsDelete;//是否删除 ：0表示删除，1表示未删除
    private Long orderCustomerId;//下单用户

    public int getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(int orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public String getOrderGroupName() {
        return orderGroupName;
    }

    public void setOrderGroupName(String orderGroupName) {
        this.orderGroupName = orderGroupName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getOrderIsDelete() {
        return orderIsDelete;
    }

    public void setOrderIsDelete(int orderIsDelete) {
        this.orderIsDelete = orderIsDelete;
    }

    public Long getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(Long orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    @Override
    public String toString() {
        return "OrderGroup{" +
                "orderGroupId=" + orderGroupId +
                ", orderGroupName='" + orderGroupName + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", orderTotalPrice=" + orderTotalPrice +
                ", orderTime=" + orderTime +
                ", orderState=" + orderState +
                ", orderIsDelete=" + orderIsDelete +
                ", orderCustomerId=" + orderCustomerId +
                '}';
    }
}
