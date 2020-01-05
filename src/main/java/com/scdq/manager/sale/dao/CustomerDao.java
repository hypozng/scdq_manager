package com.scdq.manager.sale.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sale.model.Customer;
import com.scdq.manager.sale.model.OrderCommodity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends BasicDao {
    String TABLE = "customer";

    String INSERT_COLS = "name, phone, address, integral, remark";

    String SELECT_COLS = "name, phone, address, integral, remark, " + COMMON_FIELDS;

    String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;

    @Select(SELECT_SQL)
    List<Customer> findAll();

    @Select(SELECT_SQL + " and id=#{id}")
    Customer get(@Param("id") long id);

    @Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") int id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{customer.name}, #{customer.phone}, " +
            "#{customer.address}, #{customer.integral}, #{customer.remark})")
    @SelectKey(statement = "select last_insert_id()", before = false,
            keyProperty = "customer.id", resultType = long.class)
    int insert(@Param("customer") Customer customer);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"customer.name!=null\">, name=#{customer.name}</if>"
            + "<if test=\"customer.phone!=null\">, phone=#{customer.phone}</if>"
            + "<if test=\"customer.address!=null\">, address=#{customer.address}</if>"
            + "<if test=\"customer.integral!=null\">, integral=#{customer.integral}</if>"
            + "<if test=\"customer.remark!=null\">, remark=#{customer.remark}</if>"
            + " where id=#{customer.id}</script>")
    int update(@Param("customer") Customer customer);

}
