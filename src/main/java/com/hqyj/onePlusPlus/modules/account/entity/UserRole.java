package com.hqyj.onePlusPlus.modules.account.entity;

import java.io.Serializable;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 10:18
 * version 1.0
 */
public class UserRole implements Serializable {
    private Long userId;
    private Integer roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
