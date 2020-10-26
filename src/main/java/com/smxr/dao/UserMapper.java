package com.smxr.dao;

import com.smxr.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 15:47
 */
@Mapper
@Component
public interface UserMapper {
    //    @Select("select *from user;")
    List<User> selectUserAll();
    User selectUserById(String phoneNumber);
    @MapKey("phoneNumber")
    Map<String,User> selectUserMapAll();
    @MapKey("phoneNumber")
    Map<String,User> selectUserMapById(String phoneNumber);
    User selectUserByNameOrPhoneNumber(User user);
    boolean updateUser(User user);
    User selectUserByChoose(User user);
    User selectUserByNameOrPhoneNumberTrim(User user);
    User selectUserByNameOrPhoneNumberTrimSQL(User user);
    boolean updateUserTrim(User user);
    List<User> selectUserByIds(List<String> phoneNumber);
//    --------------------------------------------
    User queryUserByPhoneNum(String s);
    ArrayList queryPowerStringByPhoneNum(int i);
}
