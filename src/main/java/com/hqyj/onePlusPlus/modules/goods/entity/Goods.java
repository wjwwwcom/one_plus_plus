package com.hqyj.onePlusPlus.modules.goods.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 10:22
 * version 1.0
 */
public class Goods implements Serializable {
    private Integer goodsId;
    private String goodsName;
    private Integer status;

    private String img;
    private String price;
    private List<GoodsParameter> Parameter;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<GoodsParameter> getParameter() {
        return Parameter;
    }

    public void setParameter(List<GoodsParameter> parameter) {
        Parameter = parameter;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", status=" + status +
                ", img='" + img + '\'' +
                ", price='" + price + '\'' +
                ", Parameter=" + Parameter +
                '}';
    }
}
