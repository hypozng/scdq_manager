package com.scdq.manager.sale.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sale.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends BasicDao {
    String TABLE = "`order`";

    String INSERT_COLS = "customer_id, total_price, final_price, status, remark";
	
	String SELECT_COLS = "customer_id as customerId, total_price as totalPrice, " +
            "final_price as finalPrice, status, remark, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<Order> findAll();

    @Select(SELECT_SQL + " and id=#{id}")
    Order get(@Param("id") long id);

    @Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") int id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{order.customerId}, " +
            "#{order.totalPrice}, #{order.finalPrice}, #{order.status}, #{order.remark})")
    @SelectKey(statement = "select last_insert_id()", before = false,
            keyProperty = "order.id", resultType = long.class)
    int insert(@Param("order") Order order);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"order.customerId!=0\">, customer_id=#{order.customerId}</if>"
            + "<if test=\"order.totalPrice!=null\">, totalPrice=#{order.totalPrice}</if>"
            + "<if test=\"order.finalPrice!=null\">, finalPrice=#{order.finalPrice}</if>"
            + "<if test=\"order.status!=null\">, finalPrice=#{order.status}</if>"
            + "<if test=\"order.remark!=null\">, finalPrice=#{order.remark}</if>"
            + " where id=#{order.id}</script>")
    int update(@Param("order") Order order);

}
