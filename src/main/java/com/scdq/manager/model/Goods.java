package com.scdq.manager.model;

import java.math.BigDecimal;

/**
 * 商品实体
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Goods extends BasicModel {
	
	// 所属种类
	private Category category;
	
	// 商品品牌
	private Brand brand;
	
	// 商品型号
	private String model;

	// 条码值
	private String barCode;
	
	// 属性
	private String property;
	
	// 进价
	private BigDecimal purchasePrice;
	
	// 卖价
	private BigDecimal salePrice;
	
	// 优惠价
	private BigDecimal preferentialPrice;
	
	// 库存数量
	private int count;
	
	// 备注
	private String remark;

	public Goods() {
		
	}
	
	public Goods(long id) {
		super(id);
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(BigDecimal preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
