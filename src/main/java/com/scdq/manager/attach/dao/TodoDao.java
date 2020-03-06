package com.scdq.manager.attach.dao;

import java.util.List;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.attach.model.Todo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoDao extends BasicDao {

	String TABLE = "todo";

	String INSERT_COLS = "id, type, status, content";

	String SELECT_COLS = "type, status, content, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;

	@Select(SELECT_SQL)
	List<Todo> findAll();

	@Select(SELECT_SQL + " and id=#{id}")
	Todo get(@Param("id") String id);

	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") String id);

	@Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{todo.id}, #{todo.type}, #{todo.status}, #{todo.content})")
	@SelectKey(statement = "select uuid()", before = true, keyProperty = "todo.id", resultType = String.class)
	int insert(@Param("todo") Todo todo);

	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"todo.type!=0\">, type=#{todo.type}</if>"
			+ "<if test=\"todo.status!=0\">, status=#{todo.status}</if>"
			+ "<if test=\"todo.content!=null\">, content=#{todo.content}</if>"
			+ " where id=#{todo.id}</script>")
	int update(@Param("todo") Todo todo);

}
