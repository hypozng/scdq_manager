package com.scdq.manager.sale.model;

import com.scdq.manager.common.BasicModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单实体
 * 
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Order extends BasicModel {

	/** 顾客姓名 */
	private String customerName;

	/** 顾客电话 */
	private String customerPhone;

	/** 顾客住址 */
	private String customerAddress;

	/** 订单价格 */
	private BigDecimal totalPrice;

	/** 成交价 */
	private BigDecimal finalPrice;

	/** 订单状态 */
	private int status;

	/** 备注 */
	private String remark;

	/** 订单商品列表 */
	private List<OrderCommodity> commodities;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<OrderCommodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<OrderCommodity> commodities) {
		this.commodities = commodities;
	}
}
