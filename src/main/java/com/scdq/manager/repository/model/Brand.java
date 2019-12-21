package com.scdq.manager.repository.model;

import com.scdq.manager.common.BasicModel;

/**
 * 品牌实体
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Brand extends BasicModel {
	
	// 品牌名称
	private String name;
	
	// 备注
	private String remark;
	
	public Brand() {
		super();
	}
	
	public Brand(long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
