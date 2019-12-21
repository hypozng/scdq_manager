package com.scdq.manager.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单实体
 * 
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Order extends BasicModel {

	// 购买数量
	private int count;

	// 订单总价
	private BigDecimal totalPrice;
	
	// 交易价格
	private BigDecimal realPrice;

	// 订单状态
	private int status;
	
	// 支付方式
	private int payment;
	
	// 购买商品列表
	private List<OrderGoods> gooses;

	public Order() {
		super();
	}

	public Order(long id) {
		super(id);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public List<OrderGoods> getGooses() {
		return gooses;
	}

	public void setGooses(List<OrderGoods> gooses) {
		this.gooses = gooses;
	}
}
