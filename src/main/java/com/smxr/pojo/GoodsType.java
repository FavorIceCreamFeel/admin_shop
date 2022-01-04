package com.smxr.pojo;

/**
 * @author smxr
 * @date 2019/12/7
 * @time 16:23
 * 商品分类表二级分类
 */
public class GoodsType {
    private int goodsTypeId;//二级分类id
    private String goodsTypeName;//二级分类名字
    private int typeId;//一级分类Id
    private String goodsTypeTime;//分类创建时间

    public GoodsType() {
    }

    public GoodsType(String goodsTypeName, int typeId, String goodsTypeTime) {
        this.goodsTypeName = goodsTypeName;
        this.typeId = typeId;
        this.goodsTypeTime = goodsTypeTime;
    }

    public GoodsType(int goodsTypeId, String goodsTypeName, int typeId, String goodsTypeTime) {
        this.goodsTypeId = goodsTypeId;
        this.goodsTypeName = goodsTypeName;
        this.typeId = typeId;
        this.goodsTypeTime = goodsTypeTime;
    }

    public int getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(int goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getGoodsTypeTime() {
        return goodsTypeTime;
    }

    public void setGoodsTypeTime(String goodsTypeTime) {
        this.goodsTypeTime = goodsTypeTime;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "goodsTypeId=" + goodsTypeId +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", typeId=" + typeId +
                ", goodsTypeTime='" + goodsTypeTime + '\'' +
                '}';
    }
}
