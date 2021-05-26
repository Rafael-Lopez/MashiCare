package com.lopez.rafael.mashicare.repositories;

import com.lopez.rafael.mashicare.entities.Order;
import com.lopez.rafael.mashicare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
}
