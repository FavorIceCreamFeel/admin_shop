package com.smxr.dao;

import com.smxr.pojo.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhangRongFei
 * @date 2019/12/13 20:24
 */
@Component
@Mapper
public interface OrdersDao {
    @Insert("insert into orders values(null,#{goodsId},#{goodsMoney},#{orderUser},#{goodsNum},#{orderTime},#{orderStatus})")
    public Boolean addOrder(Orders orders);

    @Select("select count(orderId) from orders where orderStatus=#{orderStatus}")
    public Integer orderApplicantNum(Integer orderStatus);

    @Select("select count(orderId) from orders where orderStatus=#{orderStatus}")
    public Integer orderAudit(Integer orderStatus);

    /**
     * 查询所有订单----orderStatus=0
     * @param phone
     * @return
     */
    @Select("select *from orders where orderUser=#{orderUser} and orderStatus=0")
    public List<Orders> selectOrdersAllByPhone(String phone);
    @Update("update orders set orderStatus=1 where orderId=#{orderId}")
    boolean updateOrderStatus(int orderId);
    @Update("update orders set orderStatus=2 where orderId=#{orderId}")
    boolean updateOrderStatusEnd(int orderId);
    @Delete("delete from orders where orderId=#{orderId} and orderUser=#{phoneNum}")
    boolean deleteOrder(String phoneNum,int orderId);
    /**
     * 查询所有审核订单----orderStatus=1
     * @return
     */
    @Select("select *from orders where orderStatus=1")
    public List<Orders> selectOrdersAll();
    @Select("select *from orders where orderId=1")
    public List<Orders> selectOrdersByID();
    @Insert("insert into orders values(null,#{goodsId},#{goodsMoney},#{orderUser},#{goodsNum},#{orderTime},#{orderStatus})")
    public boolean insertOrder(Orders orders);

}
