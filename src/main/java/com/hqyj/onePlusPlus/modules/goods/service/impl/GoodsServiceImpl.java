package com.hqyj.onePlusPlus.modules.goods.service.impl;

import com.hqyj.onePlusPlus.config.ResourceConfigBean;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsDao;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:24
 * version 1.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Override
    public Goods getGoodsByGoodsId(Integer goodsId) {
        return goodsDao.getGoodsByGoodsId2(goodsId);
    }

    @Override
    public List<Goods> getGoods() {
        return goodsDao.getGoods();
    }

//=======================================================

    @Override
    public List<Goods> getGood() {
        return goodsDao.getGood();
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

    //下架商品
    @Override
    @Transactional
    public Result<Goods> outGoods(Goods goods) {
        goodsDao.outGoods(goods);
        return new Result<Goods>(
                Result.ResultStatus.SUCCESS.status, "下架成功!", goods);
    }

    //重新上架商品
    @Override
    public Result<Goods> inGoods(Goods goods) {
        goodsDao.inGoods(goods);
        return new Result<Goods>(
                Result.ResultStatus.SUCCESS.status, "重新上架成功", goods);
    }

    //删除下架商品
    @Override
    public Result<Goods> deleteGoods(Goods goods) {
        goodsDao.deleteGoods(goods);
        return new Result<Goods>(
                Result.ResultStatus.SUCCESS.status, "删除成功", goods);
    }

    //查看下架商品
    @Override
    public List<Goods> isOutGoods() {
        return goodsDao.isOutGoods();
    }

    /*
     *   添加商品
     * */
    @Override
    public Result<Goods> insertGoods(Goods goods) {
        int i = goodsDao.insertGoods(goods);
        if (i == 1) {
            return new Result<Goods>(Result.ResultStatus.SUCCESS.status, "添加商品成功！", goods);
        }
        return new Result<Goods>(Result.ResultStatus.FAILED.status, "添加商品失败！", goods);
    }


    @Override
    public Result<String> uploadImg(MultipartFile file) {
        if (file.isEmpty()) {
            return new Result<String>(
                    Result.ResultStatus.FAILED.status, "Please Select Img"
            );
        }
        String relativePath = "";
        String destFilePath = "";
        try {
            String osName = System.getProperty("os.name");
            osName = osName.toLowerCase();
            if (osName.startsWith("win")) {
                destFilePath = resourceConfigBean.getLocationPathForWindows()
                        + file.getOriginalFilename();
            } else {
                destFilePath = resourceConfigBean.getLocationPathForLinux()
                        + file.getOriginalFilename();
            }

            relativePath = resourceConfigBean.getRelativePath()
                    + file.getOriginalFilename();

            File destFile = new File(destFilePath);
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(Result.ResultStatus.FAILED.status, "Upload Failed!!");
        }
        return new Result<String>(Result.ResultStatus.SUCCESS.status, "Upload Success!!", relativePath);
    }


}
