package com.scdq.manager.sale.model;

import com.scdq.manager.common.BasicModel;
import com.scdq.manager.repository.model.Commodity;

import java.math.BigDecimal;

/**
 * 订单商品
 */
public class OrderCommodity extends BasicModel {

    /** 订单ID */
    private String orderId;

    /** 商品ID */
    private String commodityId;

    /** 商品数量 */
    private int count;

    /** 总价 */
    private BigDecimal totalPrice;

    /** 成交价 */
    private BigDecimal finalPrice;

    /** 备注 */
    private String remark;

    /** 商品信息 */
    private Commodity commodity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}
