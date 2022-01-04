package com.smxr.pojo;

import java.math.BigDecimal;

/**
 * @author smxr
 * @date 2019/11/24
 * @time 1:15
 * 商品表
 */
public class Goods {
    private int goodsId;//商品id、编码
    private String goodsName;//商品名字
    private int goodsNum;//商品数量
    private BigDecimal goodsMoney;//商品价格
    private String goodsDescription;//商品描述
    private int goodsTypeId;//商品类型ID
    private String goodsUrl;//商品图片存储路径
    private int goodsStatus;//商品状态 默认上架1，0下架

    public Goods() {
    }

    public Goods(int goodsId, String goodsName, int goodsNum, BigDecimal goodsMoney, String goodsDescription, int goodsTypeId, String goodsUrl, int goodsStatus) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
        this.goodsMoney = goodsMoney;
        this.goodsDescription = goodsDescription;
        this.goodsTypeId = goodsTypeId;
        this.goodsUrl = goodsUrl;
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(BigDecimal goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public int getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(int goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsNum=" + goodsNum +
                ", goodsMoney=" + goodsMoney +
                ", goodsDescription='" + goodsDescription + '\'' +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsUrl='" + goodsUrl + '\'' +
                ", goodsStatus=" + goodsStatus +
                '}';
    }
}
