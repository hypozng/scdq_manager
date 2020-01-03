package com.scdq.manager.sale.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sale.model.OrderCommodity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCommodityDao extends BasicDao {
    String TABLE = "order_commodity";

    String INSERT_COLS = "order_id, commodity_id, count, total_price, final_price, remark";

    String SELECT_COLS = "order_id as orderId, commodity_id as commodityId, count, " +
            "total_price as totalPrice, final_price as finalPrice, remark, " + COMMON_FIELDS;

    String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;

    @Select(SELECT_SQL)
    List<OrderCommodity> findAll();

    @Select(SELECT_SQL + " and id=#{id}")
    OrderCommodity get(@Param("id") long id);

    @Delete("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") int id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{commodity.orderId}, #{commodity.commodityId}, " +
            "#{commodity.count}, #{commodity.totalPrice}, #{commodity.finalPrice}, #{commodity.remark})")
    int insert(@Param("commodity") OrderCommodity commodity);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"commodity.orderId!=0\">, order_id=#{commodity.orderId}</if>"
            + "<if test=\"commodity.commodityId!=0\">, commodity_id=#{commodity.commodityId}</if>"
            + "<if test=\"commodity.count!=0\">, count=#{commodity.count}</if>"
            + "<if test=\"commodity.totalPrice!=null\">, total_price=#{commodity.totalPrice}</if>"
            + "<if test=\"commodity.finalPrice!=null\">, final_price=#{commodity.finalPrice}</if>"
            + "<if test=\"commodity.remark!=null\">, remark=#{commodity.remark}</if>"
            + " where id=#{commodity.id}</script>")
    int update(@Param("commodity") OrderCommodity commodity);

}
