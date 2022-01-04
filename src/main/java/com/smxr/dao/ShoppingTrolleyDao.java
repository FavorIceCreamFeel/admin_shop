package com.smxr.dao;

import com.smxr.pojo.ShoppingTrolley;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author ZhangRongFei
 * @date 2019/12/13 20:24
 */
@Component
@Mapper
public interface ShoppingTrolleyDao {
    @Select("select * from shopping_trolley where shoppingTrolleyId=#{shoppingTrolleyId}")
    public ShoppingTrolley findByOrderId(Integer shoppingTrolleyId);
}
