package com.hqyj.onePlusPlus.modules.account.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 11:16
 * version 1.0
 */
@Repository
@Mapper
public interface UserRoleDao {
    @Insert("insert into user_role (user_id, role_id) values" +
            "(#{userId}, 3)")
    void insertUserRole(Long userId);

    @Insert("insert into user_role (user_id, role_id) values" +
            "(#{userId}, 2)")
    int addAdmin(Long userId);
}
