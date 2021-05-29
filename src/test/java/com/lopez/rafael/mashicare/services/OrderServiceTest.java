package com.lopez.rafael.mashicare.services;

import com.lopez.rafael.mashicare.dtos.OrderDto;
import com.lopez.rafael.mashicare.dtos.ProductOrderDto;
import com.lopez.rafael.mashicare.entities.Medicine;
import com.lopez.rafael.mashicare.entities.Order;
import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.repositories.MedicineOrderRepository;
import com.lopez.rafael.mashicare.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private static final String USERNAME = "user";

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MedicineOrderRepository medicineOrderRepository;
    @Mock
    private UserService userService;
    @Mock
    private MedicineService medicineService;
    private OrderService fixture;

    @BeforeEach
    public void setUp() {
        fixture = new OrderService(orderRepository, userService, medicineService, medicineOrderRepository);
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

    @Test
    public void shouldSaveWhenSavingOrder() {
        Integer productId = 1;

        ProductOrderDto productOrderDto = new ProductOrderDto();
        productOrderDto.setProductId(productId);
        productOrderDto.setQuantity(1);
        List<ProductOrderDto> productOrderDtoList = new ArrayList();
        productOrderDtoList.add(productOrderDto);

        Medicine medicine = new Medicine();
        medicine.setId(1);
        medicine.setName("Name");
        medicine.setDescription("Desc");
        medicine.setPrice(BigDecimal.valueOf(5));

        User user = new User();
        user.setUsername(USERNAME);

        Order order = new Order();
        order.setId(1);

        when(medicineService.findById(productId)).thenReturn(medicine);
        when(userService.findByUsername(USERNAME)).thenReturn(user);
        when(orderRepository.save(any())).thenReturn(order);

        OrderDto orderDto = new OrderDto();
        orderDto.setProducts(productOrderDtoList);
        orderDto.setUsername(USERNAME);

        fixture.saveOrder(orderDto);

        verify(medicineService).findById(1);
        verify(orderRepository).save(any());
        verify(medicineOrderRepository).save(any());
    }
}
