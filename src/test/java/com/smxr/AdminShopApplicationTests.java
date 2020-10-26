package com.smxr;

import com.smxr.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminShopApplicationTests {

    @Autowired
    User user;
    @Test
    void contextLoads() {
    }

}
