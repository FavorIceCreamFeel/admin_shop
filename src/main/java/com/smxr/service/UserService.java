package com.smxr.service;

import com.smxr.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 16:08
 */
public interface UserService extends UserDetailsService {
    public boolean insertUser(User user);
    public boolean updateUser(User user);
    public boolean selectUserByPhoneNumber(String phoneNumber);
    public User queryUserByPhoneNumber(String phoneNumber);
    public String findUserPwd(String userName,String userPwd);
    public boolean updateUserPwd(String phoneNumber,String passwordOld, String passwordOne,String passwordTwo);
    public boolean updateUserPwd(String phoneNumber, String passwordOne,String passwordTwo);
    public void getUser();
    public List<User> getUserList();
}
