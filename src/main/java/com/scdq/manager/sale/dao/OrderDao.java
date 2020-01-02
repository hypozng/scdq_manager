package com.scdq.manager.sale.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sale.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends BasicDao {
    String TABLE = "order";

    String INSERT_COLS = "customer_name, customer_phone, customer_address, total_price, final_price, status, remark";
	
	String SELECT_COLS = "customer_name as customerName, customer_phone as customerPhone, customer_address as customerAddress, " +
            "total_price as totalPrice, final_price as finalPrice, status, remark, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<Order> findAll();

    @Select(SELECT_SQL + " and id=#{id}")
    Order get(@Param("id") long id);

    @Delete("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") int id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{order.customerName}, #{order.customerPhone}, #{order.customerAddress}, " +
            "#{order.totalPrice}, #{order.finalPrice}, #{order.status}, #{order.remark})")
    int insert(@Param("order") Order order);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"order.customerName!=null\">, customer_name=#{order.customerName}</if>"
            + "<if test=\"order.customerPhone!=null\">, customer_phone=#{order.customerPhone}</if>"
            + "<if test=\"order.customerAddress!=null\">, customer_address=#{order.customerAddress}</if>"
            + "<if test=\"order.totalPrice!=null\">, totalPrice=#{order.totalPrice}</if>"
            + "<if test=\"order.finalPrice!=null\">, finalPrice=#{order.finalPrice}</if>"
            + "<if test=\"order.status!=null\">, finalPrice=#{order.status}</if>"
            + "<if test=\"order.remark!=null\">, finalPrice=#{order.remark}</if>"
            + " where id=#{order.id}</script>")
    int update(@Param("order") Order order);

}
