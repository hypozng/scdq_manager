package com.scdq.manager.common;

public interface BasicDao {
	String COMMON_FIELDS = "id, create_time as createTime, update_time as updateTime, deleted";
	
	String COL_DELETED = "deleted";
	
	String NOT_DELETED = COL_DELETED + "=0";
	
	String HAS_DELETED = COL_DELETED + "=1";
}
