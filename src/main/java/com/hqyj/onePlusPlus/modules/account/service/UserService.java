package com.hqyj.onePlusPlus.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:16
 * version 1.0
 */
public interface UserService {

    User getUserByUserId(Long userId);

    Result<User> getUserByUserNameAndPassword(User user);

    Result<User> register(User user);

    Boolean isUserNameUsable(String username);

    int active(Long userId);

    User getUserByUsername(String username);

    void logout();

    Result<String> uploadUserImg(MultipartFile file);

    Result<User> updateUserProfile(User user);

    int activeEmail(Long userId);

    Result<String> inpBackEmail(User user);

    Result<String> findBackPassword(User user);

    PageInfo<User> getAdminsBySearchVo(SearchVo searchVo);

    Result<User> addAdmin(User user);

    Result<User> editAdmin(User user);

    Result<String> deleteAdmin(Long id);

    //==================================
    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);

    //冻结用户账号
    Result<User> freezeAccount(User user);

    //解冻账号
    Result<User> thawAccount(User user);

    //查看历史订单
    List<Order> getHistoryOrders(BigInteger customerId);
}
