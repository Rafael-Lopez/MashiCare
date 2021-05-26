package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.dtos.OrderDto;
import com.lopez.rafael.mashicare.entities.*;
import com.lopez.rafael.mashicare.repositories.MedicineOrderRepository;
import com.lopez.rafael.mashicare.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private MedicineOrderRepository medicineOrderRepository;
    private UserService userService;
    private MedicineService medicineService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, MedicineService medicineService,
                        MedicineOrderRepository medicineOrderRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.medicineService = medicineService;
        this.medicineOrderRepository = medicineOrderRepository;
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

    public Order saveOrder(OrderDto orderDto) {
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        Set<MedicineOrder> medicineOrders = new HashSet();
        Order newOrder = new Order();

        orderDto.getProducts().forEach( productOrderDto -> {
            Medicine medicine = medicineService.findById(productOrderDto.getProductId());
            MedicineOrder medicineOrder = new MedicineOrder();
            medicineOrder.setMedicine(medicine);
            medicineOrder.setQuantity(productOrderDto.getQuantity());
            medicineOrders.add(medicineOrder);

            total.updateAndGet(v -> new Double((double) (v + medicine.getPrice().doubleValue() * productOrderDto.getQuantity())));
        } );

        newOrder.setUser(userService.findByUsername(orderDto.getUsername()));
        newOrder.setTotal(BigDecimal.valueOf(total.get()));
        newOrder.setDate(LocalDate.now());

        Order savedOrder = orderRepository.save(newOrder);

        medicineOrders.forEach(medicineOrder -> {
            medicineOrder.setOrder(savedOrder);

            MedicineOrderKey medicineOrderKey = new MedicineOrderKey();
            medicineOrderKey.setMedicineId(medicineOrder.getMedicine().getId());
            medicineOrderKey.setOrderId(medicineOrder.getOrder().getId());

            medicineOrder.setId(medicineOrderKey);

            medicineOrderRepository.save(medicineOrder);
        });

        return savedOrder;
    }
}
