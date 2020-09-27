package com.hqyj.onePlusPlus.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.onePlusPlus.config.ResourceConfigBean;
import com.hqyj.onePlusPlus.modules.account.dao.UserDao;
import com.hqyj.onePlusPlus.modules.account.dao.UserRoleDao;
import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.account.service.UserService;
import com.hqyj.onePlusPlus.modules.common.vo.Result;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsDao;
import com.hqyj.onePlusPlus.modules.goods.dao.GoodsParameterDao;
import com.hqyj.onePlusPlus.modules.order.dao.OrderDao;
import com.hqyj.onePlusPlus.modules.order.entity.Order;
import com.hqyj.onePlusPlus.utils.IpAddressUtils;
import com.hqyj.onePlusPlus.utils.MailUtils;
import com.hqyj.onePlusPlus.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:17
 * version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Autowired
    private GoodsParameterDao goodsParameterDao;

    private String emailTmp;

    @Override
    public User getUserByUserId(Long userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public Result<User> getUserByUserNameAndPassword(User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
        } catch (UnknownAccountException e) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "Login Failed，Account is Not Exist Or Not Active!");
        } catch (IncorrectCredentialsException e) {
            return new Result<User>(Result.ResultStatus.FAILED.status, "Login Failed, Password is Incorrect！");
        }

        Session session = subject.getSession();
        User userTmp = (User) subject.getPrincipal();
        User userDb = userDao.getUserByUserId(userTmp.getUserId());
        String roleCurr = userDb.getRoles().get(0).getRoleName();
        if ("admin".equals(roleCurr)){

        }

        session.setAttribute("user", (User) subject.getPrincipal());

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Login Success!",userDb);
    }

    @Override
    @Transactional
    public Result<User> register(User user) {
        user.setStatus(0);

        String salt = SaltUtils.getSalt(10);
        String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1024).toHex();
        user.setPassword(passwordMd5);
        user.setSalt(salt);

        userDao.insertUser(user);
        userRoleDao.insertUserRole(user.getUserId());
        try {
            MailUtils.sendMail(user.getEmail(), "<a href='http://"+ IpAddressUtils.getLocalHostLANAddress().getHostAddress()+":667/account/user/active/"
                            + user.getUserId() + "'>您正在注册One Plus Plus系统账户，点击此链接以激活账户</a>",
                    "激活 One Plus Plus 账号邮件");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return new Result<User>(Result.ResultStatus.FAILED.status, "系统主机ip地址获取失败");
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Regist Success!");
    }

    @Override
    public Boolean isUserNameUsable(String username) {
        int count = userDao.isUserNameUsable(username);
        boolean flag;
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public int active(Long userId) {
        return userDao.active(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        Session session = subject.getSession();
        session.setAttribute("user", null);
    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
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

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        User userTmp = userDao.getUserByUserId(user.getUserId());
        if (userTmp.getEmail().equalsIgnoreCase(user.getEmail())) {
            userDao.updateUser(user);
            return new Result<User>(Result.ResultStatus.SUCCESS.status, "Modify Success!");
        } else {
            emailTmp = user.getEmail();

            user.setEmail(userTmp.getEmail());
            userDao.updateUser(user);

            try {
                MailUtils.sendMail(emailTmp, "<a href='http://"+IpAddressUtils.getLocalHostLANAddress().getHostAddress()+"/account/user/activeEmail/"
                                + user.getUserId() + "'>您正在修改One Plus Plus系统账户邮箱，点击此链接以确认，若确认失败则邮箱不变</a>",
                        "确认 One Plus Plus 账号邮箱");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return new Result<User>(Result.ResultStatus.FAILED.status, "系统主机ip地址获取失败");
            }
            return new Result<User>(Result.ResultStatus.SUCCESS.status, "Modify Success！ A confirmation " +
                    "email has been sent to your modified mailbox" +
                    "，Please confirm as soon as possible!");
        }
    }

    @Override
    @Transactional
    public int activeEmail(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setEmail(emailTmp);
        return userDao.activeEmail(user);
    }

    @Override
    public Result<String> inpBackEmail(User user) {
        User userTmp = userDao.getUserByEmail(user.getEmail());
        if (userTmp != null) {
            try {
                MailUtils.sendMail(user.getEmail(), "<a href='http://"+IpAddressUtils.getLocalHostLANAddress().getHostAddress()+":667/account/user/findBackPasswordPage/"
                                + userTmp.getUserId() + "'>您正在重置One Plus Plus系统账户密码，点击此链接去修改</a>",
                        "重置 One Plus Plus 账号密码");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return new Result<String>(Result.ResultStatus.FAILED.status, "系统主机ip地址获取失败");
            }
            return new Result<String>(Result.ResultStatus.SUCCESS.status, "重置密码邮件已发送至目的邮箱");
        }
        return new Result<String>(Result.ResultStatus.FAILED.status, "您输入的邮箱无关联账号！");
    }

    @Override
    @Transactional
    public Result<String> findBackPassword(User user) {

        String salt = SaltUtils.getSalt(10);
        String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1024).toHex();
        user.setPassword(passwordMd5);
        user.setSalt(salt);

        int row = userDao.updatePasswordByuserId(user);
        if (row == 1) {
            return new Result<String>(Result.ResultStatus.SUCCESS.status, "重置密码成功！");
        }
        return new Result<String>(Result.ResultStatus.FAILED.status, "重置密码失败！");
    }

    @Override
    public PageInfo<User> getAdminsBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getAdminsBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public Result<User> addAdmin(User user) {
        user.setStatus(1);

        String salt = SaltUtils.getSalt(10);
        String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1024).toHex();
        user.setPassword(passwordMd5);
        user.setSalt(salt);

        int count = userDao.addAdmin(user);
        int count2 = userRoleDao.addAdmin(user.getUserId());
        if (count == 1 && count2 == 1) {
            return new Result<User>(Result.ResultStatus.SUCCESS.status, "Add Admin Success!", user);
        } else {
            return new Result<User>(Result.ResultStatus.FAILED.status, "Add Admin Failed!", user);
        }

    }

    @Override
    @Transactional
    public Result<User> editAdmin(User user) {
        User userTmp = userDao.getUserByUserId(user.getUserId());
        if (!user.getPassword().equals(userTmp.getPassword())) {
            String salt = SaltUtils.getSalt(10);
            String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1024).toHex();
            user.setPassword(passwordMd5);
            user.setSalt(salt);
        }
        int count = userDao.editAdmin(user);
        if (count == 1) {
            return new Result<User>(Result.ResultStatus.SUCCESS.status, "Edit Admin Success!");
        }
        return new Result<User>(Result.ResultStatus.FAILED.status, "Edit Admin FAILED!");
    }

    @Override
    @Transactional
    public Result<String> deleteAdmin(Long id) {
        int count = userDao.deleteAdmin(id);
        if (count == 1) {
            return new Result<String>(Result.ResultStatus.SUCCESS.status, "Delete Admin Success!");
        }
        return new Result<String>(Result.ResultStatus.FAILED.status, "Delete Admin Failed!");
    }

    public static void main(String[] args) {
        System.out.println(null == null);
    }

    //===========================================
    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                .orElse(Collections.emptyList()));
    }

    //冻结用户账号
    @Override
    public Result<User> freezeAccount(User user) {
        userDao.freezeAccount(user);
        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "冻结成功", user);
    }

    @Override
    public Result<User> thawAccount(User user) {
        userDao.thawAccount(user);
        return new Result<User>(
                Result.ResultStatus.SUCCESS.status, "解冻成功", user);
    }

    @Override
    public List<Order> getHistoryOrders(BigInteger customerId) {
        List<Order> orders = Optional.ofNullable(orderDao.getHistoryOrders(customerId))
                .orElse(Collections.emptyList());
        for (Order order1 : orders) {
            order1.setGoods(goodsDao.getGoods2(order1.getGoodsId())); //通过每一个order里面的goodsID来获取goods中该条数据
            order1.setGoodsPar(goodsParameterDao.getGoodsParam(order1.getParameterId())); //通过每一个order里面的goodsID来获取goods中该条数据
            order1.setUser1(userDao.getUserNameAndEmail(order1.getCustomerId()));//通过order的顾客ID获取用户表数据
        }
        return orders;
    }
}
