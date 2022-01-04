package com.smxr.service.proxy;

import com.smxr.service.UserService;
import com.smxr.service.impl.UserServiceImpl;

/**
 * @Author lzy
 * @Date 2021/1/1 14:17
 * @PC smxr
 */
public class Client {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        ServiceProxyJDK serviceProxyJDK = new ServiceProxyJDK(userService);
        UserService interfaceProxy = (UserService) serviceProxyJDK.getInterfaceProxy();
        boolean b = userService.selectUserByPhoneNumber("123456");
    }
}
