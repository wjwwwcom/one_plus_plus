package com.hqyj.onePlusPlus.modules.goods.service;

import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:25
 * version 1.0
 */
public interface GoodsParameterService {
    Result<GoodsParameter> insertGoodsParameter(GoodsParameter goodsParameter);
    GoodsParameter selectByParameterName(String parameterName);
}
