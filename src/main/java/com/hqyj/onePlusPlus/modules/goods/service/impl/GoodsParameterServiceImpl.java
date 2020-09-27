package com.hqyj.onePlusPlus.modules.goods.service.impl;

import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsParameterDao;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import com.hqyj.onePlusPlus.modules.goods.service.GoodsParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:25
 * version 1.0
 */
@Service
public class GoodsParameterServiceImpl implements GoodsParameterService {

    @Autowired
    private GoodsParameterDao goodsParameterDao;

    @Override
    public Result<GoodsParameter> insertGoodsParameter(GoodsParameter goodsParameter) {
        int i = goodsParameterDao.insertGoodsParameter(goodsParameter);
        if (i == 1) {
            return new Result<GoodsParameter>(Result.ResultStatus.SUCCESS.status, "添加商品参数成功", goodsParameter);
        }
        return new Result<GoodsParameter>(Result.ResultStatus.SUCCESS.status, "添加商品参数失败", goodsParameter);
    }

    @Override
    public GoodsParameter selectByParameterName(String parameterName) {
        return goodsParameterDao.selectByParameterName(parameterName);
    }
}
