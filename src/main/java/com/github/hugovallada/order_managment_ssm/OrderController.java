package com.github.hugovallada.order_managment_ssm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    
    @GetMapping
    public void get() {
        orderService.newOrder();
        orderService.payOrder();
        orderService.shipOrder();
        orderService.completeOrder();
    }
    
}
