package com.scdq.manager.repository.dao;

import java.util.List;

import com.scdq.manager.common.BasicDao;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.scdq.manager.repository.model.CommodityBrand;

@Repository
public interface CommodityBrandDao extends BasicDao {
	String TABLE = "commodity_brand";

	String INSERT_COLS = "id, name, remark";

	String SELECT_COLS = "name, remark, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<CommodityBrand> findAll();
	
	@Select(SELECT_SQL + " and id=#{id}")
    CommodityBrand get(@Param("id") String id);

    @Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") String id);

	@Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{brand.id}, #{brand.name}, #{brand.remark})")
	@SelectKey(statement = "select uuid()", before = true, keyProperty = "brand.id", resultType = String.class)
	int insert(@Param("brand") CommodityBrand brand);
	
	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"brand.name!=null\">, name=#{brand.name}</if>"
			+ "<if test=\"brand.remark!=null\">, remark=#{brand.remark}</if>"
			+ " where id=#{brand.id}</script>")
	int update(@Param("brand") CommodityBrand brand);
}
