package com.smxr.dao;

import com.smxr.pojo.Power;
import com.smxr.pojo.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author smxr
 * @date 2020/1/11
 * @time 16:27
 * 角色，权限字段
 */
@Mapper
@Component
public interface RoleDao {
    /**
     * 查询角色《----手机号/
     * @param phoneNumber
     * @return
     */
    @Select("select * from role where roleId in (select roleId from userid_roleid where userId=#{phoneNumber})")
    public List<Role> selectRoleByPhoneNumber(long phoneNumber);
    /**
     * 查询不属于该用户的角色《----手机号/
     * @param phoneNumber
     * @return
     */
    @Select("select * from role where roleId not in (select roleId from userid_roleid where userId=#{phoneNumber})")
    public List<Role> selectRole_ByPhoneNumber(long phoneNumber);
    /**
     * 查询所有角色
     * @return
     */
    @Select("select * from role")
    public List<Role> selectRoleAll();
    /**
     * 由角色id查询角色
     * @return
     */
    @Select("select * from role where roleId=#{roleId}")
    public Role selectRoleByRoleId(int roleId);

    /**
     * 由角色名字查询角色
     * @return
     */
    @Select("select *from role where roleName=#{roleName}")
    public Role selectRoleByRoleName(String roleName);

    /**
     * 查询所有资源字段
     * @return
     */
    @Select("select * from power")
    public List<Power> selectPowerAll();
    /**
     * 由字段id查询资源
     * @return
     */
    @Select("select * from power where powerId=#{powerId}")
    public Role selectPowerByPowerId(int powerId);

    /**
     * 由RoleId查询权限字段
     * @return
     */
    @Select("select * from power where powerId in (select distinct powerId from role_power where roleId=#{roleId})")
    public List<Power> selectPowerByRoleId(int roleId);

    /**
     * 创建Role
     * @return
     */
    @Insert("insert into role values(#{roleId},#{roleName},#{roleDescribe},#{roleStatus})")
    public boolean insertRole(Role role);

    /**
     * 修改Role角色状态
     * @return
     */
    @Update("update role set roleStatus=#{roleStatus} where roleId=#{roleId}")
    public boolean updateRole(Role role);

    /**
     * 关联角色和资源
     * @param roleId
     * @param powerId
     * @return
     */
    @Insert("insert into role_power values(#{roleId},#{powerId})")
    public boolean insertrole_power(int roleId, int powerId);

    /**
     * 用户--》添加角色
     * @param userId
     * @param roleId
     * @return
     */
    @Insert("insert into userid_roleid values(#{userId},#{roleId})")
    public boolean insertRoleId_userId(long userId,int roleId);
}
