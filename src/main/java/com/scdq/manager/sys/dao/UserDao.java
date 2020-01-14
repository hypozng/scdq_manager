package com.scdq.manager.sys.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sys.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BasicDao {
	String INSERT_COLS = "id, username, phone, email";
	
	String SELECT_COLS = "username, phone, email, " + COMMON_FIELDS;
	
	String TABLE = "user";
	
	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<User> findAll();
	
	@Select(SELECT_SQL + " and id=#{id}")
	User get(@Param("id") String id);
	
	@Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{user.id}, #{user.username}, #{user.phone}, #{user.email})")
	@SelectKey(statement = "select uuid()", before = true, keyProperty = "user.id", resultType = String.class)
	int insert(@Param("user") User user);

	@Update("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id") long id);
	
	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"user.username!=null\">, username=#{user.username}</if>"
			+ "<if test=\"user.phone!=null\">, phone=#{user.phone}</if>"
			+ "<if test=\"user.email!=null\">, email=#{user.email}</if>"
			+ " where id=#{user.id}</script>")
	int update(@Param("user") User user);

	@Select(SELECT_SQL + " and username=#{username}")
	User getByUsername(@Param("username") String username);

	@Select(SELECT_SQL + " and phone=#{phone}")
	User getByPhone(@Param("phone") String phone);

	@Select(SELECT_SQL + " and email=#{email}")
	User getByEmail(@Param("email") String email);

	@Select("select `password`=password(#{password}) from " + TABLE + " where id=#{user.id}")
	boolean verifyPassword(@Param("user") User user, @Param("password") String password);

	@Update("update " + TABLE + " set `password`=password(#{password}) where id=#{user.id}")
	boolean modifyPassword(@Param("user") User user, @Param("password") String password);
}
