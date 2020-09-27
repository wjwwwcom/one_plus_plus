package com.hqyj.onePlusPlus.modules.order.controller;


import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;
import com.hqyj.onePlusPlus.modules.order.service.OrderGroupService;
import com.hqyj.onePlusPlus.modules.order.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:26
 * version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGroupService orderGroupService;

     //跳转购物车页面
     @GetMapping("/mycart")
    public String MyCartPage(){
        return "index";
    }

    //购物车数据查询
    @PostMapping(value = "/mycarts")
    @ResponseBody
    public Result<List<Order>> getCartsBySearchVo() {
        Result<List<Order>>listResult= orderService.FindOrderByMyCart();
        return listResult;
    }
     //删除单项商品
    @DeleteMapping("/dela/{orderId}")
    @ResponseBody
    public Result<Order> DeleteCartByOrderId(@PathVariable int orderId){
        return orderService.DeleteCartByOrderId(orderId);
    }
     //多项清空删除
    @DeleteMapping("/delall")
    @ResponseBody
    public Result<Order> DelAllCartByOrderId(@RequestParam String doai){
        return orderService.DelAllCartByOrderId(doai);
    }
     //多项购买支付
    @PutMapping("/purchase")
    @ResponseBody
    public Result<Order> PurchaseByOrder(@RequestParam String orderstr){
        return orderService.PurchaseByOrder(orderstr);
    }

    //跳转商品详情页面
    @GetMapping("/detail")
    public String ToGoodsdetail(@RequestParam int goodsId,Long customerId , ModelMap map){
             Subject subject = SecurityUtils.getSubject();
             Session session = subject.getSession();
             User user = (User) session.getAttribute("user");
             customerId = user.getUserId();
         Goods goods =orderService.FindGoodsByGoodsId(goodsId);
         Result<User> result =orderService.FindUserByUserId(customerId);
        List<GoodsParameter> goodsParlist=orderService.FindGoodsParByGoodsId(goodsId);
         map.addAttribute("goods",goods);
         map.addAttribute("imgdesc",goodsParlist.get(0));
         map.addAttribute("customer",result.getObject());
         map.addAttribute("goodsParList",goodsParlist);
         map.addAttribute("template","order/detail");
        return "index";
    }
     //查询用户地址信息
    @PostMapping("/customer/{customerId}")
    @ResponseBody
    public Result<User> GetUserInformation(@PathVariable int customerId) {
         return orderService.FindUserByUserId(customerId);
    }
        //单件购买支付
    @PostMapping(value = "/purchases" )
    @ResponseBody
    public Result<Boolean> PurchaseGoods( Order order){
        return orderService.PurchaseGoods(order);
    }

    //加入购物车
    @PostMapping(value = "/addcart" )
    @ResponseBody
    public Result<Boolean> AddCart( Order order){
        return orderService.AddCart(order);
    }

    //====================================================================
    /*
     *127.0.0.1:667/order/allOrderDetailPage
     *未付款订单(购物车)
     */
    @GetMapping("/allOrderDetailPage")
    public String orderDetailPage(){
        return "index";
    }

    /*
     *127.0.0.1:667/order/allOrderPage
     *未付款订单(购物车)
     */
    @GetMapping("/allOrderPage")
    public String orderPage(){
        return "index";
    }

    /*
     *127.0.0.1:667/order/getAllOrders
     * 订单组页面
     */
    @PostMapping(value = "/getAllOrders",consumes = "application/json")
    @ResponseBody
    public PageInfo<OrderGroup> getOrders(@RequestBody SearchVo searchVo){
        return orderGroupService.getAllOrders(searchVo);
    }

    /*
     * 127.0.0.1:667/order/getOrders
     * 订单组详情
     */
    @PostMapping(value = "/getOrders")
    @ResponseBody
    public List<Order> getOneOrderGroup(@RequestParam String orderNumber){
        return orderService.getOneOrderGroup(orderNumber);
    }

    //=======================================================================
}
