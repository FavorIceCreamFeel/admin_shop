package com.smxr.dao;

import com.smxr.pojo.Types;
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
 * 一级分类dao
 */
@Component
@Mapper
public interface TypesDao {
    @Select("select *from types")
    public List<Types> selectTypesAll();
    @Delete("delete from types where typeId=#{typeId}")
    boolean delTypesById(int typeId);
    @Insert("insert into types values(null,#{typeName},#{typeCreateTime})")
    boolean insertTypes(Types types);
    @Insert("insert into types values(null,#{typeName},#{typeCreateTime})")
    boolean insertTypesTwe(Types types);
}
