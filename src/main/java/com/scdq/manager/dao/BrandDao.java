package com.scdq.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.scdq.manager.model.Brand;

@Repository
public interface BrandDao extends BasicDao {
	String COLS = "name, remark";
	
	String COLUMNS = "name, remark, " + COMMON_FIELDS;
	
	String TABLE = "brand";
	
	String SELECT_SQL = "select " + COLUMNS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<Brand> findAll();
	
	@Select(SELECT_SQL + " and id=#{id}")
	Brand get(@Param("id") long id);
	
	@Insert("insert into " + TABLE + "(" + COLS + ") values(#{brand.name}, #{brand.remark})")
	int insert(@Param("brand") Brand brand);
	
	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"brand.name!=null\">, name=#{brand.name}</if>"
			+ "<if test=\"brand.remark!=null\">, remark=#{brand.remark}</if>"
			+ " where id=#{brand.id}</script>")
	int update(@Param("brand") Brand brand);
	
	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") long id);
}
