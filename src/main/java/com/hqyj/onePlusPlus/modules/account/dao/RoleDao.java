package com.hqyj.onePlusPlus.modules.account.dao;

import com.hqyj.onePlusPlus.modules.account.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:15
 * version 1.0
 */
@Repository
@Mapper
public interface RoleDao {

    @Select("SELECT\n" +
            "role.role_id,\n" +
            "role.role_name,\n" +
            "role.`desc`\n" +
            "FROM\n" +
            "user_role\n" +
            "INNER JOIN role ON user_role.role_id = role.role_id\n" +
            "where user_role.user_id = #{userId}")
    List<Role> getRolesByUserId(Long userId);
}
