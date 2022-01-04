package com.smxr;

import com.smxr.controller.AdminShopController;
import com.smxr.dao.UserMapper;
import com.smxr.pojo.User;
import com.smxr.service.impl.ClientServiceImpl;
import com.smxr.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lzy
 * @Date 2020/12/15 21:57
 * @PC smxr
 */
@SpringBootTest
public class Spring_IOC {
        @Autowired
        private UserMapper userMapper;
        @Autowired
        UserMapper userMappers;

        @Test
        void spring_12_26() {
                AdminShopController adminShopController = new AdminShopController();
//                adminShopController.setUserService(new ClientServiceImpl());
//                adminShopController.setUserService(new UserServiceImpl());
//                adminShopController.spring_ioc();
        }

        @Test
        void spring_12_29() {
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(1);
                integers.add(6);
                integers.add(9);
                integers.add(2);
                integers.add(5);

                //stream 过滤                   和    聚合(.collect(Collectors.toList()))
                List<Integer> collect = integers.stream().filter(filter -> filter >= 5).collect(Collectors.toList());
                Long collect1 = integers.stream().filter(filter -> filter >= 5).count();
                collect.forEach(System.out::println);
                System.out.println("stream 过滤集合后："+collect.size());
                System.out.println("stream 过滤集合后："+collect.size());
        }
        @Test
        void spring_1_3() {
                String str=" ";
                String string="123,342,234,555,234,234,123,";
                System.out.println(StringUtils.isEmpty(str));
                System.out.println(StringUtils.isEmpty(string));
                final StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
                System.out.println(stringBuilder.toString());

        }
        @Test
        void contextLoads_23() {
                List<User> users = userMapper.selectUserAll();
                users.sort(new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {
                                //o1>o2 是返回1 是升序/是返回-1是降序
                                return o1.getUserAge()>o2.getUserAge()?1:-1;
                        }
                });
                users.forEach(System.out::println);
        }

        @Test
        void contextLoads_2333() {
                String str="1234567890";
                String substring = str.substring(0, 5);
                System.out.println(substring);
        }

}
