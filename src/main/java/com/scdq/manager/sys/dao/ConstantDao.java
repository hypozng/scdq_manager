package com.scdq.manager.sys.dao;

import com.scdq.manager.common.BasicDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ConstantDao extends BasicDao {
	String TABLE = "constants";
	
	@Select("select `value` from " + TABLE + " where `key`=#{key}")
	String get(@Param("key") String  key);
	
	@Update("update " + TABLE + " set `value`=#{value} where `key`=#{key}")
	int update(@Param("key") String key, @Param("value") String value);
	
	@Insert("insert into " + TABLE + "(`key`, `value`) values(#{key}, #{value})")
	int insert(@Param("key") String key, @Param("value") String value);
	
	@Delete("delete from " + TABLE + " where `key`=#{key}")
	int remove(@Param("key") String key);
	
	@Select("select count(1) > 0 from " + TABLE + " where `key`=#{key}")
	boolean contains(@Param("key") String key);
}
