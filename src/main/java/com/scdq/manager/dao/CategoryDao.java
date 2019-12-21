package com.scdq.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.scdq.manager.model.Category;

@Repository
public interface CategoryDao extends BasicDao {
	String COLS = "name, remark";

	String COLUMNS = COLS + ", " + COMMON_FIELDS;

	String TABLE = "category";

	String SELECT_SQL = "select " + COLUMNS + " from " + TABLE + " where " + NOT_DELETED;

	@Select(SELECT_SQL + " and id=#{id}")
	Category get(@Param("id") long id);

	@Select(SELECT_SQL)
	List<Category> findAll();

	@Insert("insert into " + TABLE + "(" + COLS + ") values(#{category.name}, #{category.remark})")
	int insert(@Param("category") Category category);

	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"category.name!=null\">, name=#{category.name}</if>"
			+ "<if test=\"category.remark!=null\">, remark=#{category.remark}</if>"
			+ " where id=#{category.id}</script>")
	int update(@Param("category") Category category);

	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") long id);

}
