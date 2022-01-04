package com.smxr.service.proxy;

import com.smxr.pojo.User;
import com.smxr.service.UserService;
import com.smxr.service.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @Author lzy
 * @Date 2021/1/1 13:05
 * @PC smxr
 * 静态代理模式
 */
public class UserServiceProxy implements UserService {
    private UserServiceImpl  userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean insertUser(User user) {
        System.out.println("代理实现类 UserServiceProxy ");
        return userService.insertUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        System.out.println("代理实现类 UserServiceProxy ");
          return userService.insertUser(user);
    }

    @Override
    public boolean selectUserByPhoneNumber(String phoneNumber) {
        System.out.println("代理实现类 UserServiceProxy ");
        return   userService.selectUserByPhoneNumber( phoneNumber);
    }

    @Override
    public User queryUserByPhoneNumber(String phoneNumber) {
        System.out.println("代理实现类 UserServiceProxy ");
        return   userService.queryUserByPhoneNumber( phoneNumber);
    }

    @Override
    public String findUserPwd(String userName, String userPwd) {
        System.out.println("代理实现类 UserServiceProxy ");
        return   userService.findUserPwd( userName,  userPwd);
    }

    @Override
    public boolean updateUserPwd(String phoneNumber, String passwordOld, String passwordOne, String passwordTwo) {
        System.out.println("代理实现类 UserServiceProxy ");
        return   userService.updateUserPwd( phoneNumber,  passwordOld,  passwordOne,  passwordTwo);
    }

    @Override
    public boolean updateUserPwd(String phoneNumber, String passwordOne, String passwordTwo) {
        System.out.println("代理实现类 UserServiceProxy ");
        return userService.updateUserPwd( phoneNumber,  passwordOne,  passwordTwo);
    }

    @Override
    public void getUser() {

    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

}

//静态代理
class ceshi{
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        //userService.selectUserByPhoneNumber("123132123");
        //不用在对UserServiceImpl的改动下，添加了对业务处理的逻辑
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        userServiceProxy.setUserService(userService);
        userServiceProxy.selectUserByPhoneNumber("123132123");

    }
}
