package com.smxr.dao;



import com.smxr.pojo.Goods;
import com.smxr.pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface GoodsDao {

    //查询所有商品
    @Select("select * from goodtype left join goods on goodtype.goodsTypeId=goods.goodTypeId")
    List<Goods> findAll();

    /**
     * 查询所有商品
     * @return
     */
    @Select("select * from goods")
    List<Goods> findGoodsAll();
    //订单查询

    @Select("select  o.orderId,o.goodsNum,o.goodsMoney,g.goodsUrl from  goods  join  orders o on o.goodsId=g.goodsId")
    List<Orders> findAllOrder();

    /**
     * 由商品号查询商品
     * @return
     */
    @Select("select *from goods where goodsId=#{goodsId}")
    Goods selectGoodsById(int goodsId);

    @Select("select *from goods where goodsName=#{goodsName}")
    Goods selectGoodsByName(String goodsName);

    /**
     * 由商品类型查找商品
     * @param goodsTypeId
     * @return
     */
    @Select("select * from goods where goodsTypeId=#{goodsTypeId} and goodsStatus!=0")
    List<Goods> selectGoodsByType(int goodsTypeId);

    /**
     * byID修改商品数量
     * @param goodsId
     * @param goodsNum
     * @return
     *  and goodsNum > 0
     */
    @Update("update goods set goodsNum=goodsNum-#{goodsNum} where goodsId=#{goodsId}")
    boolean updateGoodsNumById(int goodsId,int goodsNum);

    /**
     * byID修改商品状态
     * @param goodsId
     * @param goodsStatus
     * @return
     */
    @Update("update goods set goodsStatus=#{goodsStatus} where goodsId=#{goodsId}")
    boolean updateGoodsStatusById(int goodsId,int goodsStatus);

    /**
     * byID查询商品状态
     * @param goodsId
     * @return
     */
    @Select("select goodStatus from goods where goodsId=#{paraml}")
    int queryGoodsStatusById(int goodsId);

    @Insert("insert into goods values(null,#{goodsName},#{goodsNum},#{goodsMoney},#{goodsTypeId},#{goodsUrl},#{goodsDescription},#{goodsStatus})")
    boolean insertGoods(Goods goods);
}
