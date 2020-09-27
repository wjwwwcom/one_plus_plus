package com.hqyj.onePlusPlus.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.account.service.UserService;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.utils.IpAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:21
 * version 1.0
 */
@Controller
@RequestMapping("/account/user")
public class UserController {

    @Autowired
    private UserService userService;

//=================  登陆、注册 ========================
    /*
    *   通过userId 获取user及其role
    * */
    @GetMapping("/user/{userId}")
    @ResponseBody
    public User getUserByUserId(@PathVariable Long userId) {
        return userService.getUserByUserId(userId);
    }

    /*
    *   跳转到登陆界面
    * */
    @GetMapping("/login")
    public String login() {
        return "indexSimple";
    }

    /*
    *   跳转到注册界面
    * */
    @GetMapping("/register")
    public String register() {
        return "indexTest";
    }

    /*
    *   注册
    * */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> register(@RequestBody User user) {
        Result<User> register = userService.register(user);
        return register;
    }

    /*
    *  查看username是否可用
    * */
    @PostMapping("/isUserNameUsable")
    @ResponseBody
    public Map<String, Object> isUserNameUsable(String username) {
        Boolean flag = userService.isUserNameUsable(username);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        map.put("msg", "判断用户名是否可用成功");
        return map;
    }

    /*
    *   激活账号
    * */
    @GetMapping("/active/{userId}")
    @ResponseBody
    public String active(@PathVariable Long userId){
        int count = userService.active(userId);
        if (count == 1){
            return "账号激活成功";
        }
        return "账号激活失败";
    }


    /**
     * 登陆
     * 127.0.0.1:667/api/login   ---- post
     * {"userName":"admin","password":"111111"}
     */
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> login(@RequestBody User user){
        return userService.getUserByUserNameAndPassword(user);
    }

    /*
    *   logout
    * */
    @GetMapping("/logout")
    public String logout(ModelMap modelMap){
        modelMap.addAttribute("template","account/user/login");
        userService.logout();
        return "indexSimple";
    }

//=================  登陆、注册 结束========================

//=================  个人页面 ========================

    /*
    *   跳转到个人页面
    * */
    @GetMapping("/profile")
    public String profilePage(){
        return "index";
    }

    /*
     *   跳转到个人页面
     * */
    @GetMapping("/profile2")
    public String profilePage2(){
        return "index";
    }

    /*
    *   上传用户头像
    * */
    @PostMapping(value = "/uploadUserImg", consumes = "multipart/form-data")
    @ResponseBody
    public Result<String> uploadUserImg(@RequestParam MultipartFile file){
        return userService.uploadUserImg(file);
    }

    /**
     *  修改个人信息
     * 127.0.0.1/api/profile ---- put
     */
    @PutMapping(value = "/profile", consumes = "application/json")
    @ResponseBody
    public Result<User> updateUserProfile(@RequestBody User user) {
        return userService.updateUserProfile(user);
    }

    /*
     *   激活账号
     * */
    @GetMapping("/activeEmail/{userId}")
    @ResponseBody
    public String activeEmail(@PathVariable Long userId){
        int count = userService.activeEmail(userId);
        if (count == 1){
            return "邮箱确认成功";
        }
        return "邮箱确认失败";
    }


    /*
     *   得到 找回密码的 邮箱
     * */
    @PostMapping(value = "/inpBackEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<String> inpBackEmail(@RequestBody User user){
        return userService.inpBackEmail(user);
    }

    /*
     *   跳转到找回密码页面  不需要登录
     * */
    @GetMapping("/findBackPasswordPage/{userId}")
    public String findBackPassword(@PathVariable Long userId, ModelMap modelMap){
        modelMap.addAttribute("template","account/user/findBackPasswordPage");
        modelMap.addAttribute("userId", userId);
        return "indexTest";
    }

    /*
     *   得到 找回密码的 邮箱
     * */
    @PostMapping(value = "/findBackPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<String> findBackPassword(@RequestBody User user){
        return userService.findBackPassword(user);
    }



//=================  个人页面 结束========================


//=================  管理管理员 ========================


    /*
    *   跳转到admins列表
    * */
    @GetMapping("admins")
    public String admins(){
        return "index";
    }

    /**
     * admins列表
     * 127.0.0.1:667/account/user/admins   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @PostMapping(value = "/admins",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageInfo<User> getAdminsBySearchVo(@RequestBody SearchVo searchVo) {
        return userService.getAdminsBySearchVo(searchVo);
    }

    /**
     * admins列表
     * 127.0.0.1:667/account/user/admins   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @PostMapping(value = "/admin",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> addAdmin(@RequestBody User user) {
        return userService.addAdmin(user);
    }

    /**
     * admins列表
     * 127.0.0.1:667/account/user/admins   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @PutMapping(value = "/admin",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> editAdmin(@RequestBody User user) {
        return userService.editAdmin(user);
    }

    /**
     * admins列表
     * 127.0.0.1:667/account/user/admins   ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @DeleteMapping(value = "/admin/{id}")
    @ResponseBody
    public Result<String> deleteAdmin(@PathVariable Long id) {
        return userService.deleteAdmin(id);
    }




//=================  管理管理员 结束 ========================



    public static void main(String[] args) throws UnknownHostException {
        try {
            // 这种IP容易拿错
            // System.out.println("Current IP address : " +
            // InetAddress.getLocalHost().getHostAddress());
            // 不一定准确的IP拿法
            // 出自比如这篇：http://www.cnblogs.com/zrui-xyu/p/5039551.html

            // 正确的IP拿法
            System.out.println("get LocalHost LAN Address : " + IpAddressUtils.getLocalHostLANAddress().getHostAddress());


        } catch (UnknownHostException e) {

            e.printStackTrace();

        }
    }

    //==========================用户管理===============================
//    @GetMapping("/user/{userId}")
//    @ResponseBody
//    public User getUserByUserId(@PathVariable Long userId) {
//        return userService.getUserByUserId(userId);
//    }

    /*
     *127.0.0.1:667/account/user/allUsers
     */
    @GetMapping("/allUsers")
    public String allUsers(){
        return "index";
    }

    /*
     *127.0.0.1:667/account/user/getAllUsers
     */
    @PostMapping(value = "/getAllUsers",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUsersBySearchVo(searchVo);
    }

    //冻结用户账号
    @PutMapping(value = "/freezeAccount",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> freezeAccount(@RequestBody User user){
        return userService.freezeAccount(user);
    }

    //冻结用户账号
    @PutMapping(value = "/thawAccount",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> thawAccount(@RequestBody User user){
        return userService.thawAccount(user);
    }

    @PostMapping(value = "/getHistoryOrders")
    @ResponseBody
    public List<Order> getHistoryOrders(@RequestParam BigInteger customerId){
        return userService.getHistoryOrders(customerId);
    }


}
