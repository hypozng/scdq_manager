package com.scdq.manager.model;

/**
 * 商品种类
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Category extends BasicModel {
	
	// 种类名称
	private String name;
	
	// 备注
	private String remark;
	
	public Category() {
		super();
	}
	
	public Category(long id) {
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
