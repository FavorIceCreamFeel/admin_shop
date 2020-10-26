package com.smxr.service.impl;

import com.smxr.dao.UserMapper;
import com.smxr.pojo.User;
import com.smxr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 16:09
 */
@Service
public class UserServeiceImpl implements  UserService {
    @Autowired
    UserMapper userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean insertUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean selectUserByPhoneNumber(String phoneNumber) {
        return false;
    }

    @Override
    public User queryUserByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public String findUserPwd(String userName, String userPwd) {
        return null;
    }

    @Override
    public boolean updateUserPwd(String phoneNumber, String passwordOld, String passwordOne, String passwordTwo) {
        return false;
    }

    @Override
    public boolean updateUserPwd(String phoneNumber, String passwordOne, String passwordTwo) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s==null){ return null;}
        if (s.equals("")){ return null;}
        User user = userDao.queryUserByPhoneNum(s);
        if (user==null){ return null;}
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList powerStringList = userDao.queryPowerStringByPhoneNum(Integer.parseInt(s));
        String join = String.join(",",powerStringList);
//        for (int i = 0; i <powerStringList.size() ; i++) {
//            if (i==powerStringList.size()-1){
//                stringBuilder.append(powerStringList.get(i));
//            }else {
//                stringBuilder.append(powerStringList.get(i));
//                stringBuilder.append(",");
//            }
//        }
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber()+"", user.getUserPwd(), AuthorityUtils.commaSeparatedStringToAuthorityList(join));
    }
}
