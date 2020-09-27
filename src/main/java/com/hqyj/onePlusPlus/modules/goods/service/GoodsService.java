package com.hqyj.onePlusPlus.modules.goods.service;

import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:23
 * version 1.0
 */
public interface GoodsService {

    Goods getGoodsByGoodsId(Integer goodsId);
    List<Goods> getGoods();


    List<Goods> getGood();

    List<Goods> getAllGoods();

    //下架商品
    Result<Goods> outGoods(Goods goods);

    //重新上架商品
    Result<Goods> inGoods(Goods goods);

    //删除下架商品
    Result<Goods> deleteGoods(Goods goods);

    //查看下架商品
    List<Goods> isOutGoods();

    Result<Goods> insertGoods(Goods goods);

    Result<String> uploadImg(MultipartFile file);
}
