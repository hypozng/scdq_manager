package com.scdq.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.scdq.manager.model.Todo;

@Repository
public interface TodoDao extends BasicDao {
	
	String COLS = "type, status, content";

	String COLUMNS = COLS + ", " + COMMON_FIELDS;

	String TABLE = "todo";

	String SELECT_SQL = "select " + COLUMNS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Insert("insert into " + TABLE + "(" + COLS + ") values(#{todo.type}, #{todo.status}, #{todo.content})")
	int insert(@Param("todo") Todo todo);
	
	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") long id);
	
	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"todo.type!=0\">, type=#{todo.type}</if>"
			+ "<if test=\"todo.status!=0\">, status=#{todo.status}</if>"
			+ "<if test=\"todo.content!=null\">, content=#{todo.content}</if>"
			+ " where id=#{todo.id}</script>")
	int update(@Param("todo") Todo todo);
	
	@Select(SELECT_SQL)
	List<Todo> findAll();
}
