package com.smxr.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author smxr
 * @date 2019/11/24
 * @time 1:24
 * 购物车表
 */
public class ShoppingTrolley {
    private int shoppingTrolleyId;//订单id
    private int goodsId;//订单商品id
    private BigDecimal goodsMoney;//订单商品价格
    private int goodsNum;//订单商品数量
    private Date orderTime;//订单创建时间
    private int orderStatus;//订单状态（0未支付，1申请支付中，2已经支付）

    public ShoppingTrolley() {
    }

    public ShoppingTrolley(int shoppingTrolleyId, int goodsId, BigDecimal goodsMoney, int goodsNum, Date orderTime, int orderStatus) {
        this.shoppingTrolleyId = shoppingTrolleyId;
        this.goodsId = goodsId;
        this.goodsMoney = goodsMoney;
        this.goodsNum = goodsNum;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }

    public int getShoppingTrolleyId() {
        return shoppingTrolleyId;
    }

    public void setShoppingTrolleyId(int shoppingTrolleyId) {
        this.shoppingTrolleyId = shoppingTrolleyId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(BigDecimal goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
