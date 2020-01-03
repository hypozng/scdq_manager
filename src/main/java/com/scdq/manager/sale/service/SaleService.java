package com.scdq.manager.sale.service;

import com.scdq.manager.common.ResponseData;
import com.scdq.manager.repository.dao.CommodityDao;
import com.scdq.manager.sale.dao.OrderCommodityDao;
import com.scdq.manager.sale.dao.OrderDao;
import com.scdq.manager.sale.model.Order;
import com.scdq.manager.sale.model.OrderCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderCommodityDao orderCommodityDao;

    @Autowired
    private CommodityDao commodityDao;

    /**
     * 添加订单
     * @param order 订单信息
     * @return
     */
    public ResponseData<Order> addOrder(Order order) {
        if (order == null) {
            return new ResponseData<>("order = null");
        }
        if (order.getCommodities() == null || order.getCommodities().isEmpty()) {
            return new ResponseData<>("订单中缺少商品信息");
        }
        order.setTotalPrice(BigDecimal.ZERO);
        for (OrderCommodity commodity : order.getCommodities()) {
            commodity.setCommodity(commodityDao.get(commodity.getCommodityId()));
            if (commodity.getCommodity() == null) {
                return new ResponseData<>("订单中使用了不存在的商品(commodityId=" + commodity.getCommodityId() + ")");
            }
            commodity.setTotalPrice(commodity.getCommodity().getSalePrice().multiply(new BigDecimal(commodity.getCount())));
            if (commodity.getFinalPrice() == null
                    || commodity.getFinalPrice().equals(BigDecimal.ZERO)) {
                commodity.setFinalPrice(commodity.getTotalPrice());
            }
            order.setTotalPrice(order.getTotalPrice().add(commodity.getFinalPrice()));
        }
        if (order.getFinalPrice() == null
                || order.getFinalPrice().equals(BigDecimal.ZERO)) {
            order.setFinalPrice(order.getTotalPrice());
        }
        int result = orderDao.insert(order);
        if (result == 0) {
            return new ResponseData<>("添加订单失败");
        }
        for(OrderCommodity commodity : order.getCommodities()) {
            if (commodity == null) {
                continue;
            }
            commodity.setOrderId(order.getId());
            result = orderCommodityDao.insert(commodity);
            if (result == 0) {
                return new ResponseData<>("添加订单商品失败(commodityId=" + commodity.getCommodityId() + ")");
            }
        }
        return new ResponseData<>(order);
    }

    /**
     * 获取订单列表
     * @return
     */
    public ResponseData<List<Order>> getOrders() {
        return new ResponseData<>(orderDao.findAll());
    }
}
