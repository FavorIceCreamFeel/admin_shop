package com.smxr.controller;

import com.smxr.pojo.ResponseBean;
import com.smxr.pojo.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 16:32
 */
@RestController
@RequestMapping("/vue")
public class AdminShopController {

    @RequestMapping("/info")
    public ResponseBean vueTest(){
        return ResponseUtils.Success(null,"admin-shop is for vue test!");
    }
    @RequestMapping("/signIn")
    public ResponseBean vueLogin(@RequestParam String account,@RequestParam String pass){
        if (account==null||pass==null){
            return ResponseUtils.Error(null,"账户密码不能为空！");
        }
        if(!account.equals("772519606@qq.com")||!pass.equals("123456")) return ResponseUtils.Error(null,"账户密码不能为空！");
        System.out.println("账户密码:"+account+pass);
        byte[] bytes = (account + pass).getBytes();
        String string = Base64.getEncoder().encodeToString(bytes);
        System.out.println("string:"+string);
        return ResponseUtils.Success(string);
    }
}
