package com.scdq.manager.sys.dao;

import com.scdq.manager.common.BasicDao;
import com.scdq.manager.sys.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BasicDao {
	String COLS = "username, phone, email";
	
	String COLUMNS = "username, phone, email, " + COMMON_FIELDS;
	
	String TABLE = "user";
	
	String SELECT_SQL = "select " + COLUMNS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<User> findAll();
	
	@Select(SELECT_SQL + " and id=#{id}")
	User get(@Param("id") long id);
	
	@Insert("insert into " + TABLE + "(" + COLS + ") values(#{user.username}, #{user.phone}, #{user.email})")
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
