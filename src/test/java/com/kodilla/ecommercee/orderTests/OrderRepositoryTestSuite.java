package com.kodilla.ecommercee.orderTests;

import com.kodilla.ecommercee.domain.CartItem;
import com.kodilla.ecommercee.domain.CartStatus;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderRepositoryTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testOrderRepositorySave() {
        //Given
        CartItem cartItem = new CartItem();
        Order order = new Order();
        order.getCartItems().add(cartItem);
        cartItem.setOrder(order);

        //When
        orderRepository.save(order);
        long orderId = order.getId();
        long cartItemId = cartItem.getId();

        //Then
        try {
            assertNotEquals(orderId, 0);
            assertNotEquals(cartItemId, 0);
        } finally {
            //Cleanup
            orderRepository.deleteById(orderId);
        }
    }

    @Test
    void testOrderRepositoryFindAll() {
        //Given
        Order order1 = new Order();
        Order order2 = new Order();
        orderRepository.save(order1);
        orderRepository.save(order2);
        long order1Id = order1.getId();
        long order2Id = order2.getId();

        //When
        List<Order> orders = orderRepository.findAll();
        List<Long> iDs = orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        //Then
        try {
            assertTrue(iDs.contains(order1Id) && iDs.contains(order2Id));
        } finally {
            //CleanUp
            orderRepository.deleteById(order1Id);
            orderRepository.deleteById(order2Id);
        }
    }

    @Test
    void testOrderRepositoryFindById() {
        //Given
        Order order = new Order();
        orderRepository.save(order);
        long orderId = order.getId();

        //When
        Optional<Order> orderFoundById = orderRepository.findById(orderId);

        //Then
        try {
            assertTrue(orderFoundById.isPresent());
        } finally {
            //Cleanup
            orderRepository.deleteById(orderId);
        }
    }

    @Test
    void testOrderRepositoryDeleteById() {
        //Given
        Order order1 = new Order();
        Order order2 = new Order();
        orderRepository.save(order1);
        orderRepository.save(order2);
        long order1Id = order1.getId();
        long order2Id = order2.getId();

        //When
        orderRepository.deleteById(order1Id);
        Optional<Order> order1FoundById = orderRepository.findById(order1Id);
        Optional<Order> order2FoundById = orderRepository.findById(order2Id);

        //Then
        try {
            assertFalse(order1FoundById.isPresent());
            assertTrue(order2FoundById.isPresent());
        } finally {
            //Cleanup
            orderRepository.deleteById(order2Id);
        }
    }

    @Test
    void testOrderRepositorySaveWithIdPresent() {
        //Given
        CartItem cartItem = new CartItem();
        Order order = new Order();
        order.getCartItems().add(cartItem);
        cartItem.setOrder(order);
        orderRepository.save(order);
        long orderId = order.getId();

        //When
        CartItem cartItem1 = new CartItem();
        cartItem1.setOrder(order);
        order.getCartItems().add(cartItem1);
        order.setCartStatus(CartStatus.ORDER);

        orderRepository.save(order);
        Order orderRetrieved = orderRepository.findById(orderId).orElse(new Order());

        //Then
        try {
            assertEquals(2, orderRetrieved.getCartItems().size());
            assertEquals(CartStatus.ORDER, orderRetrieved.getCartStatus());
        } finally {
            //Cleanup
            orderRepository.deleteById(orderId);
        }
    }

}
