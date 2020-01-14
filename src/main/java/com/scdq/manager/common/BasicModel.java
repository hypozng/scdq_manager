package com.scdq.manager.common;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础实体类
 * 
 * @author zenghaibo
 * @date 2019-04-10
 */
public class BasicModel {
	// ID
	private String id;

	// 创建时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	// 修改时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	// 为true时表示该实体已在数据库中被删除
	@JsonIgnore()
	private boolean deleted;

	public BasicModel() {

	}

	public BasicModel(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BasicModel) {
			BasicModel model = (BasicModel) obj;
			return model.getId() != null && model.getId().equals(getId());
		}
		return super.equals(obj);
	}
}
