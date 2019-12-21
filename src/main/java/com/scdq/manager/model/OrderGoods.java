package com.scdq.manager.model;

import java.math.BigDecimal;

/**
 * 订单商品
 * @author zenghaibo
 * @date 2019-04-15
 */
public class OrderGoods extends BasicModel {

	// 商品
	private Goods goods;
	
	// 购买数量
	private int count;
	
	// 合计价格
	private BigDecimal totalPrice;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
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
}
