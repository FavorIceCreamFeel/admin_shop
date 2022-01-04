package com.smxr.dao;

import com.smxr.pojo.GoodsType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author smxr
 * @date 2019/12/18
 * @time 18:29
 * 二级分类dao
 */
@Component
@Mapper
public interface GoodsTypeDao {
    @Select("select *from goodstype where typeId=#{typeId}")
    public List<GoodsType> selectGoodsTypeByTypeId(int typeId);

    @Select("select *from goodstype")
    List<GoodsType> selectGoodsTypeAll();
    @Delete("delete from goodstype where goodsTypeId=#{goodsTypeId}")
    boolean delGoodsTypesById(int goodsTypeId);
    @Insert("insert into goodstype values(null,#{goodsTypeName},#{typeId},#{goodsTypeTime})")
    boolean insertGoodsTypes(GoodsType goodsType);
}
