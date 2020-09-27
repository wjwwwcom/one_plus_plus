package com.hqyj.onePlusPlus.modules.goods.dao;

import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:24
 * version 1.0
 */
@Repository
@Mapper
public interface GoodsParameterDao {

    @Select("select * from goods_parameter where parameter_id = #{parameterId}")
    GoodsParameter getGoodsParameterByparameterId(Integer parameterId);

    @Update("update goods_parameter set storage=#{storage} where parameter_id = #{parameterId}")
    Boolean UpdateGoodsParByGodsParmeId(GoodsParameter goodsParameter);

    @Select("select * from goods_parameter where goods_id =#{goodsId}")
    List<GoodsParameter> getGoodsParameterByGoodsId(Integer goodsId);

    @Select("SELECT\n" +
            "goods_parameter.parameter_id,\n" +
            "goods_parameter.goods_price,\n" +
            "goods_parameter.`storage`,\n" +
            "goods_parameter.goods_img,\n" +
            "goods_parameter.goods_id,\n" +
            "goods_parameter.parameter_name,\n" +
            "goods_parameter.`desc`\n" +
            "FROM\n" +
            "goods_parameter WHERE goods_id = #{goodsId}")
    List<GoodsParameter> getParameter(Integer goodsId);

    //============================
    @Select("select * from goods_parameter where parameter_id = #{parameterId}")
    GoodsParameter getGoodsParam(Integer parameterId);

    @Insert("insert into goods_parameter (goods_price,storage,goods_img,goods_id,parameter_name,goods_parameter.desc) " +
            "VALUES (#{goodsPrice},#{storage},#{goodsImg},#{goodsId},#{parameterName},#{desc})")
    int insertGoodsParameter(GoodsParameter goodsParameter);

    @Select("Select * from goods_parameter where parameter_name = #{parameterName}")
    GoodsParameter selectByParameterName(String parameterName);
}
