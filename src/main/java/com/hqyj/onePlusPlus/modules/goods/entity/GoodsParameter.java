package com.hqyj.onePlusPlus.modules.goods.entity;

import java.io.Serializable;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 10:23
 * version 1.0
 */
public class GoodsParameter implements Serializable {
    private Integer parameterId;
    private Double goodsPrice;
    private Integer storage;
    private String goodsImg;
    private Integer goodsId;
    private String parameterName;
    private String desc;


    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GoodsParameter{" +
                "parameterId=" + parameterId +
                ", goodsPrice=" + goodsPrice +
                ", storage=" + storage +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsId=" + goodsId +
                ", parameterName='" + parameterName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
