package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.entities.Order;
import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private static final String USERNAME = "user";

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    private OrderService fixture;

    @BeforeEach
    public void setUp() {
        fixture = new OrderService(orderRepository, userService);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFindingAndUsernameIsNull() {
        Exception exception = assertThrows( IllegalArgumentException.class, () -> fixture.findAllByUsername(null) );

        String expectedMessage = "Username must be valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenFindingAndUserDoesNotExist() {
        when(userService.findByUsername(USERNAME)).thenReturn(null);

        Exception exception = assertThrows( EntityNotFoundException.class, () -> fixture.findAllByUsername(USERNAME) );

        String expectedMessage = "Entity not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldRetrieveOrdersWhenUserExists() {
        User expectedResult = new User();
        expectedResult.setUsername(USERNAME);

        Order order = new Order();
        order.setId(1);
        order.setUser(expectedResult);

        List<Order> orderList = new ArrayList();
        orderList.add(order);

        when(userService.findByUsername(USERNAME)).thenReturn(expectedResult);
        when(orderRepository.findAllByUser(expectedResult)).thenReturn(orderList);

        List<Order> result = fixture.findAllByUsername(USERNAME);

        assertNotNull(result);
        assertTrue(result.size() == 1);
    }
}
