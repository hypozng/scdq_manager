package com.scdq.manager.common;

public interface BasicDao {
	String COL_DELETED = "`deleted`";

	String NOT_DELETED = COL_DELETED + "=0";

	String HAS_DELETED = COL_DELETED + "=1";

	String COMMON_FIELDS = "`id`, `create_time` as `createTime`, `update_time` as `updateTime`, " + COL_DELETED;

}
