package com.hqyj.onePlusPlus.modules.order.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;

import java.io.Serializable;
import java.util.Date;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 10:27
 * version 1.0
 */
public class Order implements Serializable {
    private Integer orderId;
    //订单号
    private String orderNumber;
    private Long customerId;
    private Integer orderState;
    private Integer parameterId;
    private Integer goodsId;
    //对应参数的商品数量
    private Integer number;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date buyTime;
    private Double totalPrice;
    private String address;
    private String phone;
    //连表查询开始
    private Goods goods;
    private GoodsParameter goodsPar;
    private User user1;//用户

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsParameter getGoodsPar() {
        return goodsPar;
    }

    public void setGoodsPar(GoodsParameter goodsPar) {
        this.goodsPar = goodsPar;
    }
      //结束


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", customerId=" + customerId +
                ", orderState=" + orderState +
                ", parameterId=" + parameterId +
                ", goodsId=" + goodsId +
                ", number=" + number +
                ", buyTime=" + buyTime +
                ", totalPrice=" + totalPrice +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
