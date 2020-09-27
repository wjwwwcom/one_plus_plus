package com.hqyj.onePlusPlus.modules.goods.controller;

import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import com.hqyj.onePlusPlus.modules.goods.service.GoodsParameterService;
import com.hqyj.onePlusPlus.modules.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:24
 * version 1.0
 */
@Controller
@RequestMapping("/goodsParameter")
public class GoodsParameterController {
    @Autowired
    private GoodsParameterService goodsParameterService;
    @Autowired
    private GoodsService goodsService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/goodsParameter")
    @ResponseBody
    public Result<GoodsParameter> insertGoodsParameter(@RequestBody GoodsParameter goodsParameter) {
        ;
        return goodsParameterService.insertGoodsParameter(goodsParameter);
    }

}
