package com.hqyj.onePlusPlus.modules.order.controller;


import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.account.service.UserService;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;
import com.hqyj.onePlusPlus.modules.order.service.OrderGroupService;
import com.hqyj.onePlusPlus.modules.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderGroupController {
    @Autowired
    private OrderGroupService orderGroupService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    /**
     * 跳转到订单页面
     * http://localhost:667/order/orderPage --- GET
     */
    @GetMapping("/order")
    public String toOrderPage(){
        return "index";
    }

    /**
     * 跳转到单个商品详情页面
     */
//    @GetMapping("/detail")
//    public String toDetailPage(){
//        return "index";
//    }

    /**
     * 展示：订单列表，分页，查询
     */
    @PostMapping(value = "/getOrderGroups",consumes = "application/json")
    @ResponseBody
    public PageInfo<OrderGroup> getOrders(@RequestBody SearchVo searchVo){
        return orderGroupService.getOrderGroupBySearchVo(searchVo);
    }

    /**
     * 跳转到订单详情页面
     */
    @GetMapping("/orderdetails")
    public String toOrderDetailPage(@RequestParam String orderCode,String orderState, ModelMap model){
        model.addAttribute("orderCode", orderCode);
        return "index";
    }

    /**
     * 展示：一个订单中购买的一个或多个商品信息
     */
    @PostMapping(value = "/getGoods")
    @ResponseBody
    public List<Order> showOrderDetail(@RequestParam String orderNumber){
        return orderService.getOrders(orderNumber);
    }

    /**
     * 修改：确认收货，修改订单状态
     */
    @PutMapping("/updateState")
    @ResponseBody
    public Result<OrderGroup> updateState(@RequestParam String orderCode){
        return orderGroupService.updateState(orderCode);
    }

    /**
     * 删除：通过订单编号删除
     */
    @DeleteMapping("/deleteOrderGroup")
    @ResponseBody
    public Result<OrderGroup> deleteOrderGroup(@RequestParam String orderCode){
        return orderGroupService.deleteOrderGroupByOrderCode(orderCode);
    }

}
