package com.hqyj.onePlusPlus.modules.order.service.impl;


import com.hqyj.onePlusPlus.modules.account.dao.UserDao;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsDao;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsParameterDao;
import com.hqyj.onePlusPlus.modules.goods.entity.Goods;
import com.hqyj.onePlusPlus.modules.goods.entity.GoodsParameter;
import com.hqyj.onePlusPlus.modules.order.dao.OrderDao;
import com.hqyj.onePlusPlus.modules.order.dao.OrderGroupDao;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.modules.order.entity.OrderGroup;
import com.hqyj.onePlusPlus.modules.order.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:27
 * version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsParameterDao goodsParameterdao;
    @Autowired
    private OrderGroupDao orderGroupDao;


    //查询购物车
    @Override
    public Result<List<Order>> FindOrderByMyCart() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute("user");
        List<Order> list=orderDao.FindOrderByMyCart(user.getUserId());
        if (list.size()!=0){
            for (Order order : list) {
                order.setGoods(goodsDao.getGoodsByGoodsId(order.getGoodsId()));
                order.setGoodsPar(goodsParameterdao.getGoodsParameterByparameterId(order.getParameterId()));
            }
            return  new Result<List<Order>>(
                    Result.ResultStatus.SUCCESS.status, "Select success.", list);
        }else {
            return  new Result<List<Order>>(
                    Result.ResultStatus.FAILED.status, "Select fail.",null);
        }
    }

    @Override
    @Transactional
    public Result<Order> DeleteCartByOrderId(int orderId) {
        Boolean boo =orderDao.DeleteCartByOrderId(orderId);
        if (boo){
            return new Result<>(
                    Result.ResultStatus.SUCCESS.status, "Delete success.");
        }else {
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Delete fail.");
        }
    }

    @Override
    @Transactional
    public Result<Order> DelAllCartByOrderId(String doai) {
        List<Integer> list =new ArrayList<>();
        List<String> lists = Arrays.asList(doai.split(","));
        for (String s : lists) {
            list.add(Integer.parseInt(s));
        }
        boolean boo=true;
        for (int i : list){
            boo=orderDao.DeleteCartByOrderId(i);
            if (boo == false){break;}
        }
        if (boo){
            return new Result<>(
                    Result.ResultStatus.SUCCESS.status, "Delete success.");
        }else {
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Delete fail.");
        }
    }

    @Override
    @Transactional
    public Result<Order> PurchaseByOrder(String orderstr) {
        Order order =new Order();
        OrderGroup orderGroup = new OrderGroup();

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute("user");

        orderGroup.setOrderCustomerId(user.getUserId());//下单用户
        //设置订单号
        String ordercode = "2020";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ordercode += String.valueOf(random.nextInt(10));
        }
         orderGroup.setOrderCode(ordercode);
         orderGroup.setOrderState(1);
         orderGroup.setOrderIsDelete(1);
         orderGroup.setOrderTime(new Date());
         //切割前端传递的数据
        List<String> list1 = Arrays.asList(orderstr.split(";"));
        orderGroup.setOrderTotalPrice(Double.parseDouble(list1.get(list1.size()-1)));
        String goodsname = "";
        int com=list1.size();
        boolean boo =true;
        for (String list : list1) {
            com--;
            if (com==0){break;}
            List<String> s = Arrays.asList(list.split(","));
            order.setOrderId(Integer.parseInt(s.get(0)));
            order.setNumber(Integer.parseInt(s.get(2)));
            order.setTotalPrice(Double.parseDouble(s.get(3)));
            order.setAddress(s.get(4));
            order.setPhone(s.get(5));
            order.setOrderNumber(orderGroup.getOrderCode());
            order.setBuyTime(orderGroup.getOrderTime());
            goodsname+=s.get(1)+"<br>";
            //修改order相应字段
            boo = orderDao.UpdateOrderByOrderId(order);
            if (boo==false){break;}
            if (StockManage(order.getOrderId(),order.getNumber())==false){break;}
        }
        orderGroup.setOrderGroupName(goodsname);
        if (com!=0){
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "order Update fail.");
        }
        boo =orderGroupDao.InsertOrderGroup(orderGroup);
        if (boo){
            return new Result<>(
                    Result.ResultStatus.SUCCESS.status, "Insert success.");
        }else {
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Insert fail.");
        }
    }
     //库存管理
    @Override
    @Transactional
    public Boolean StockManage(int orderId,int number) {
        int parameterId=orderDao.FindOrderByOrderId(orderId).getParameterId();
        int stock=goodsParameterdao.getGoodsParameterByparameterId(parameterId).getStorage();
        GoodsParameter goodsParameter =new GoodsParameter();
        goodsParameter.setParameterId(parameterId);
        goodsParameter.setStorage((stock-number));
        return goodsParameterdao.UpdateGoodsParByGodsParmeId(goodsParameter);
    }

    @Override
    public Order FindOrderByOrderId(int orderId) {
        Order order =orderDao.FindOrderByOrderId(orderId);
        order.setGoods(goodsDao.getGoodsByGoodsId(order.getGoodsId()));
        order.setGoodsPar(goodsParameterdao.getGoodsParameterByparameterId(order.getParameterId()));
        return order;
    }

    @Override
    public Result<User> FindUserByUserId(long userId) {
       User user =userDao.getUserByUserId(userId);
        if (user!=null){
            return new Result<>(
                    Result.ResultStatus.SUCCESS.status, "Select success.",user);
        }else {
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Select fail.");
        }
    }

    @Override
    public List<GoodsParameter> FindGoodsParByGoodsId(Integer goodsId) {
        return goodsParameterdao.getGoodsParameterByGoodsId(goodsId);
    }

    @Override
    public Goods FindGoodsByGoodsId(Integer goodsId) {
        return goodsDao.getGoodsByGoodsId(goodsId);
    }

    @Override
    @Transactional
    public Result<Boolean> PurchaseGoods(Order order){
        //首先设置orderGroup数据
         OrderGroup orderGroup = new OrderGroup();
        //设置订单号
        String ordercode = "2020";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ordercode += String.valueOf(random.nextInt(10));
        }
        orderGroup.setOrderCode(ordercode);
        orderGroup.setOrderTime(new Date());
        orderGroup.setOrderState(1);
        orderGroup.setOrderIsDelete(1);
        orderGroup.setOrderGroupName(order.getGoods().getGoodsName());
        orderGroup.setOrderTotalPrice(order.getTotalPrice());
        orderGroup.setOrderCustomerId(order.getCustomerId());
        //设置order表的数据
        order.setBuyTime(orderGroup.getOrderTime());
        order.setOrderNumber(orderGroup.getOrderCode());
        order.setNumber(1);
        order.setOrderState(1);
        Boolean boo =orderGroupDao.InsertOrderGroup(orderGroup);
        if (boo==false){
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Insert fail.");
        }else {
            boo=orderDao.InsertOrder(order);
            if (boo){
                return new Result<>(
                        Result.ResultStatus.SUCCESS.status, "Insert success.");
            }else {
                return new Result<>(
                        Result.ResultStatus.FAILED.status, "Insert fail.");
            }
        }
    }

    @Override
    public Result<Boolean> AddCart(Order order) {
        order.setOrderState(0);
        order.setNumber(1);
        order.setBuyTime(new Date());
        Boolean boo=orderDao.InsertOrder(order);
        if (boo){
            return new Result<>(
                    Result.ResultStatus.SUCCESS.status, "Insert success.");
        }else {
            return new Result<>(
                    Result.ResultStatus.FAILED.status, "Insert fail.");
        }
    }

    //订单开始
    // 获取：一个订单中购买的一个或多个商品信息
    @Override
    public List<Order> getOrders(String orderNumber) {
        List<Order> orders = Optional.ofNullable(orderDao.getOrders(orderNumber))
                .orElse(Collections.emptyList());
        for (Order order: orders) {
            order.setGoods(goodsDao.getGoodsByGoodsId(order.getGoodsId()));
            order.setGoodsPar(goodsParameterdao.getGoodsParameterByparameterId(order.getParameterId()));
        }
        return orders;
    }
    //订单结束


    //================================================================================
    @Override
    public List<Order> getOneOrderGroup(String orderNumber) {
        List<Order> orders = Optional.ofNullable(orderDao.getOrders(orderNumber))
                .orElse(Collections.emptyList());
        for (Order order : orders) {
            order.setGoods(goodsDao.getGoods2(order.getGoodsId())); //通过每一个order里面的goodsID来获取goods中该条数据
            order.setGoodsPar(goodsParameterdao.getGoodsParameterByparameterId(order.getParameterId())); //通过每一个order里面的goodsID来获取goods中该条数据
            order.setUser1(userDao.getUserNameAndEmail(order.getCustomerId()));//通过order的顾客ID获取用户表数据
        }
        return orders;
    }
}
