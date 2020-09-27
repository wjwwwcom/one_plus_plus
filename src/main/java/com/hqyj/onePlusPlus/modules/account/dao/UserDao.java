package com.hqyj.onePlusPlus.modules.account.dao;

import com.hqyj.onePlusPlus.modules.account.entity.User;
import com.hqyj.onePlusPlus.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:15
 * version 1.0
 */
@Repository
@Mapper
public interface UserDao {


    @Select("select * from user where user_id=#{userId}")
    @Results(id = "userResults", value = {
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_id", property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.hqyj.onePlusPlus.modules.account.dao.RoleDao.getRolesByUserId"))})
    User getUserByUserId(Long userId);


    @Select("select count(*) from user where username = #{username}")
    int isUserNameUsable(String username);

    @Insert("insert into user (username, password, phone_number, email, salt, status) " +
            "values(#{username}, #{password}, #{phoneNumber}, #{email}, #{salt}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    void insertUser(User user);

    @Update("update user set status = 1 where user_id = #{userId}")
    int active(Long userId);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Update("update user set username = #{username}, phone_number=#{phoneNumber}, email=#{email}," +
            "address=#{address}, user_img=#{userImg} where user_id=#{userId}")
    void updateUser(User user);

    @Update("update user set email=#{email} where user_id=#{userId}")
    int activeEmail(User user);

    @Select("select * from user where email=#{email}")
    User getUserByEmail(@Param("email") String email);

    @Update("update user set password=#{password}, salt=#{salt} where user_id=#{userId}")
    int updatePasswordByuserId(User user);

    @Select("<script> SELECT\n" +
            "`user`.user_id,\n" +
            "`user`.username,\n" +
            "`user`.`password`,\n" +
            "`user`.phone_number,\n" +
            "`user`.email,\n" +
            "`user`.salt,\n" +
            "`user`.user_img,\n" +
            "`user`.`status`,\n" +
            "`user`.address\n" +
            "FROM\n" +
            "`user`\n" +
            "INNER JOIN user_role ON `user`.user_id = user_role.user_id\n" +
            "INNER JOIN role ON user_role.role_id = role.role_id\n"
            + "<where> "
            + "and role.role_name = 'admin'"
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (username like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by user_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getAdminsBySearchVo(SearchVo searchVo);

    @Insert("insert into user (username, password, phone_number, salt, status) " +
            "values(#{username}, #{password}, #{phoneNumber}, #{salt}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    int addAdmin(User user);

    @Update("update user set username=#{username}, phone_number=#{phoneNumber}, " +
            "password=#{password}, status=#{status} where user_id=#{userId}")
    int editAdmin(User user);

    @Delete("delete from user where user_id=#{id}")
    int deleteAdmin(Long id);

    //==========================================================
    @Select("select * from user where user_id = #{userId}")
    User getUserNameAndEmail(Long userId);

    @Select("<script>" +
            "select * from user "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (username like '%${keyWord}%') "
            + " or (user_id like '%${keyWord}%') "
            + " or (phone_number like '%${keyWord}%') "
            + " or (email like '%${keyWord}%') "
            + " or (address like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by user_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearchVo(SearchVo searchVo);

    //冻结账号（修改激活状态就行）
    @Select("update user set `status` = 0 where user_id = #{userId}")
    void freezeAccount(User user);

    //冻结账号（修改激活状态就行）
    @Select("update user set `status` = 1 where user_id = #{userId}")
    void thawAccount(User user);
    //============================================
}
