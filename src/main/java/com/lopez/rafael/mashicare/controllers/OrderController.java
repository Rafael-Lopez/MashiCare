package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.dtos.OrderDto;
import com.lopez.rafael.mashicare.entities.Order;
import com.lopez.rafael.mashicare.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/orders/{username}")
    public List<Order> getOrdersByUsername(@PathVariable String username) {
        return orderService.findAllByUsername(username);
    }

    @PostMapping(path = "/order")
    public Order createNewOrder(@RequestBody OrderDto orderDto) {
        return orderService.saveOrder(orderDto);
    }
}
