package com.scdq.manager.repository.dao;

import java.util.List;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.repository.model.CommodityCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityCategoryDao extends BasicDao {
	String TABLE = "commodity_category";

	String INSERT_COLS = "id, name, remark";

	String SELECT_COLS = "name, remark, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;

	@Select(SELECT_SQL)
	List<CommodityCategory> findAll();

	@Select(SELECT_SQL + " and id=#{id}")
    CommodityCategory get(@Param("id") String id);

	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") String id);

	@Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{category.id}, #{category.name}, #{category.remark})")
	@SelectKey(statement = "select uuid()", before = true, keyProperty = "category.id", resultType = String.class)
	int insert(@Param("category") CommodityCategory category);

	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"category.name!=null\">, name=#{category.name}</if>"
			+ "<if test=\"category.remark!=null\">, remark=#{category.remark}</if>"
			+ " where id=#{category.id}</script>")
	int update(@Param("category") CommodityCategory category);

}
