package com.hqyj.onePlusPlus.modules.goods.controller;

import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:23
 * version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 跳转到商品列表页面
     *127.0.0.1:667/goods/shopping   ---- get
     */
    @GetMapping("/shopping")
    public String shoppingPage(){
        return "index";
    }

    /**
     * 跳转到商品详情
     *127.0.0.1:667/goods/detailPage   ---- get
     */
//    @GetMapping("/detail")
//    public String detailPage(ModelMap map){
//        map.addAttribute("template","order/detail");
//        return "index";
//    }

    /**
     * 根据商品id获取商品详情参数
     * 127.0.0.1:667/goods/goods/1
     * @param goodsId
     * @return
     */
    @GetMapping("/goods/{goodsId}")
    @ResponseBody
    public Goods getGoodsByGoodsId(@PathVariable Integer goodsId) {
        return goodsService.getGoodsByGoodsId(goodsId);
    }

    /**
     * 商品列表，展示商品
     * 127.0.0.1:667/goods/goods
     * @return
     */
    @GetMapping("/goods")
    @ResponseBody
    public List<Goods> getGoods() {
        return goodsService.getGoods();
    }


//==============================================================================
/*
 * 127.0.0.1:667/goods/goodsHome
 */
@GetMapping("/goodsHome")
public String goodsHome(){
    return "index";
}

    /*
     * 127.0.0.1:667/goods/goodsGround
     */
    @GetMapping("/goodsGround")
    public String goodsGround(){
        return "index";
    }

    /*
     * 127.0.0.1:667/goods/goodsDetail
     */
    @GetMapping("goodsDetail")
    public String goodsDetail(){
        return "index";
    }



    /**
     * 127.0.0.1:667/goods/good
     * @return
     */
    @GetMapping("/good")
    @ResponseBody
    public List<Goods> getGoods1() {
        return goodsService.getGood();
    }

    /**
     * 127.0.0.1:667/goods/getAllGoods
     * @return
     */
    @GetMapping("/getAllGoods")
    @ResponseBody
    public List<Goods> getAllGoods(){
        return goodsService.getAllGoods();
    }

    //下架商品
    @PutMapping(value = "/outGoods",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Goods> outGoods(@RequestBody Goods goods){
        return goodsService.outGoods(goods);
    }

    //重新上架已经下架的商品
    @PutMapping(value = "/inGoods",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Goods> inGoods(@RequestBody Goods goods){
        return goodsService.inGoods(goods);
    }

    //删除下架的商品（实则是修改状态为0，不是真的从数据库删除）
    @PutMapping(value = "/deleteGoods",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Goods> deleteGoods(@RequestBody Goods goods){
        return goodsService.deleteGoods(goods);
    }

    //删除下架的商品（实则是修改状态为0，不是真的从数据库删除）
    @GetMapping("/isOutGoods")
    @ResponseBody
    public List<Goods> isOutGoods() {
        return goodsService.isOutGoods();
    }

    /*
    *  跳转 admin添加商品、商品参数页面
    * */
    @GetMapping("/addGoodsPage")
    public String addGoodsPage(){
        return "index";
    }
    @GetMapping("/addGoodsParameterPage")
    public String addGoodsParameterPage(){
        return "index";
    }


    //单个文件上传
    //针对form表单上传文件consumes = "multipart/form-data"
    //@RequestParam MultipartFile file
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public String uploadFile(@RequestParam MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        //如果没有选择文件
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select file！！！");
            return "redirect:/goods/addGoodsPage";
        }
        try {
            String destFilePath = "F:\\uploadTestForCode\\" + file.getOriginalFilename();
            File destFile = new File(destFilePath);
            file.transferTo(destFile);
            redirectAttributes.addFlashAttribute("message", "Upload file success！！！");
        } catch (IOException e) {
            e.printStackTrace();
            //如果上传失败
            redirectAttributes.addFlashAttribute("message", "Upload file failed！！！");
        }
        //上传成功
        return "redirect:/goods/addGoodsPage";
    }

    /*
     *   上传商品（参数）图片
     * */
    @PostMapping(value = "/uploadImg", consumes = "multipart/form-data")
    @ResponseBody
    public Result<String> uploadUserImg(@RequestParam MultipartFile file){
        return goodsService.uploadImg(file);
    }

    /*
    *   上传商品
    * */
    @PostMapping(value = "/goods", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Goods> update(@RequestBody Goods goods) {
        return goodsService.insertGoods(goods);
    }
}
