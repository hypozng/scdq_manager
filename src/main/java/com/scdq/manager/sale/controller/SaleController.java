package com.scdq.manager.sale.controller;

import com.scdq.manager.common.ResponseData;
import com.scdq.manager.sale.model.Order;
import com.scdq.manager.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @RequestMapping("/addOrder")
    public ResponseData<Order> addOrder(@RequestBody Order order) {
        return saleService.addOrder(order);
    }

    @RequestMapping("/getOrders")
    public ResponseData<List<Order>> getOrders() {
        return saleService.getOrders();
    }
}
