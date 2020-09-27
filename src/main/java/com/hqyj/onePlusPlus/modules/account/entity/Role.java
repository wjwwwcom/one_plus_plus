package com.hqyj.onePlusPlus.modules.account.entity;

import java.io.Serializable;

/**
 * author  Jayoung
 * createDate  2020/9/14 0014 10:20
 * version 1.0
 */
public class Role implements Serializable {
    private Integer roleId;
    private String roleName;
    private String desc;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
