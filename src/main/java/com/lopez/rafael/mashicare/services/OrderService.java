package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.Order;
import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> findAllByUsername(String username) {
        if( username == null ) {
            throw new IllegalArgumentException("Username must be valid");
        }

        User user = userService.findByUsername(username);

        if( user == null ) {
            throw new EntityNotFoundException("Entity not found");
        }

        return orderRepository.findAllByUser(user);
    }
}
