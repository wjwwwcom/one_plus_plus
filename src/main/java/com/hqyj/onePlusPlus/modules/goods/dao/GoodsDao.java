package com.hqyj.onePlusPlus.modules.goods.dao;

import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:23
 * version 1.0
 */
@Repository
@Mapper
public interface GoodsDao {

    @Select("select * from goods where goods_id = #{goodsId}")
    Goods getGoodsByGoodsId(int goodsId);

    @Select("select * from goods where goods_id=#{goodsId} ")
    @Results(id = "goodsResults", value = {
            @Result(column = "goods_id", property = "goodsId"),
            @Result(column = "goods_id", property = "Parameter",
                    javaType = List.class,
                    many = @Many(select = "com.hqyj.onePlusPlus.modules.goods.dao.GoodsParameterDao.getParameter"))
    })
    Goods getGoodsByGoodsId2(Integer goodsId);

    @Select("select * from goods")
    List<Goods> getGoods();


    //    ==================================================

    @Select("select * from goods where goods_id = #{goodsId}")
    Goods getGoods2(Integer goodsId);

    //查询上架的商品
    @Select("select * from goods where status = '1'")
    List<Goods> getGood();

    //查询所有商品（除了被管理员删除的商品）
    @Select("select * from goods where status = '1' or status = '2'")
    List<Goods> getAllGoods();

    //下架商品
    @Select("update goods set status = 2 where goods_id = #{goodsId}")
    void outGoods(Goods goods);

    //上架商品（经测验，修改也能用@Select注解）
    @Select("update goods set status = 1 where goods_id = #{goodsId}")
    void inGoods(Goods goods);

    //删除商品
    @Update("update goods set status = 0 where goods_id = #{goodsId}")
    void deleteGoods(Goods goods);

    //查看所有下架商品
    @Select("select * from goods where status = '2'")
    List<Goods> isOutGoods();

    @Insert("insert into goods (goods_name,status,img,price) VALUES (#{goodsName},1,#{img},#{price})")
    int insertGoods(Goods goods);
}
