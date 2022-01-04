package com.smxr.pojo;

/**
 * @author smxr
 * @date 2019/12/18
 * @time 18:09
 * 一级分类
 */
public class Types {
    private int typeId;//一级分类ID
    private String typeName;//一级名字
    private String typeCreateTime;//一级分类创建时间

    public Types() {
    }

    public Types(String typeName, String typeCreateTime) {
        this.typeName = typeName;
        this.typeCreateTime = typeCreateTime;
    }

    public Types(int typeId, String typeName, String typeCreateTime) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeCreateTime = typeCreateTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCreateTime() {
        return typeCreateTime;
    }

    public void setTypeCreateTime(String typeCreateTime) {
        this.typeCreateTime = typeCreateTime;
    }

    @Override
    public String toString() {
        return "Types{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typeCreateTime='" + typeCreateTime + '\'' +
                '}';
    }
}
