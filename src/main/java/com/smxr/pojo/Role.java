package com.smxr.pojo;

/**
 * @author smxr
 * @date 2019/11/24
 * @time 1:32
 * 角色表
 */
public class Role {
    private int roleId;//角色id
    private String roleName;//角色名字
    private String roleDescribe;//角色描述
    private int roleStatus;//角色状态

    public Role() {
    }

    public Role(int roleId, int roleStatus) {
        this.roleId = roleId;
        this.roleStatus = roleStatus;
    }

    public Role(int roleId, String roleName, String roleDescribe, int roleStatus) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescribe = roleDescribe;
        this.roleStatus = roleStatus;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public int getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(int roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescribe='" + roleDescribe + '\'' +
                ", roleStatus=" + roleStatus +
                '}';
    }
}
