package com.scdq.manager.sys.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sys.model.Menu;
import com.scdq.manager.sys.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends BasicDao {
    String INSERT_COLS = "`name`, `identifier`, `url`, `parent_id`, `remark`, `order`";

    String SELECT_COLS = "`name`, `identifier`, `url`, `parent_id` as `parentId`, `remark`, `order`, " + COMMON_FIELDS;

    String TABLE = "`menu`";

    String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;

    String ORDER_BY_SQL = "order by `order`, `parent_id`";

    @Select(SELECT_SQL + " " + ORDER_BY_SQL)
    List<Menu> findAll();

    @Select(SELECT_SQL + " and `id`=#{id}")
    Menu get(@Param("id") String id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{menu.name}, #{menu.identifier}, " +
            "#{menu.url}, #{menu.parentId}, #{menu.remark}, #{menu.order})")
    @SelectKey(statement="select uuid()", before = true, keyProperty = "menu.id", resultType = String.class)
    int insert(@Param("menu") Menu menu);

    @Update("update " + TABLE + " set " + HAS_DELETED + " where `id`=#{id}")
    int delete(@Param("id") String id);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"menu.name!=null\">, name=#{menu.name}</if>"
            + "<if test=\"menu.identifier!=null\">, identifier=#{menu.identifier}</if>"
            + "<if test=\"menu.url!=null\">, url=#{menu.url}</if>"
            + "<if test=\"menu.parentId!=null\">, parent_id=#{menu.parentId}</if>"
            + "<if test=\"menu.remark!=null\">, remark=#{menu.remark}</if>"
            + " where id=#{menu.id}</script>")
    int update(@Param("menu") Menu menu);

    @Select("select max(`order`) as `order` from " + TABLE + " where `parent_id`=#{parentId}")
    int getMaxOrder(@Param("parentId") String parentId);

//    int updateOrders(@Param("order"));
}
