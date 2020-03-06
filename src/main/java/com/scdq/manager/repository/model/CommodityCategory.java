package com.scdq.manager.repository.model;

import com.scdq.manager.common.BasicModel;

/**
 * 商品种类
 * @author zenghaibo
 * @date 2019-04-10
 */
public class CommodityCategory extends BasicModel {
	
	// 种类名称
	private String name;
	
	// 备注
	private String remark;
	
	public CommodityCategory() {
		super();
	}

	public CommodityCategory(String id) {
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
