package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartItem;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.dto.CartItemDto;
import com.kodilla.ecommercee.domain.dto.OrderDto;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.CartItemMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.CartItemDbService;
import com.kodilla.ecommercee.service.OrderDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderDbService dbService;
    private final OrderMapper orderMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemDbService cartItemDbService;

    @GetMapping
    public List<OrderDto> getOrders() {
        List<Order> allOrdersList = dbService.getAllOrders();
        return orderMapper.mapToOrderDtoList(allOrdersList);
    }

    @GetMapping(value = "{orderId}")

    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(dbService.getOrder(orderId)));
    }

    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        dbService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) throws Exception {
        Order order = orderMapper.mapToOrder(orderDto);
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            CartItem cartItem = cartItemMapper.mapToCartItem(cartItemDto);
            cartItem.setOrder(order);
            cartItemDbService.saveCartItem(cartItem);
            order.getCartItems().add(cartItem);
        }
        Order savedOrder = dbService.saveOrder(order);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(savedOrder));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto) throws UserNotFoundException, ProductNotFoundException, OrderNotFoundException {
        Order order = orderMapper.mapToOrder(orderDto);
        dbService.saveOrder(order);
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            CartItem cartItem = cartItemMapper.mapToCartItem(cartItemDto);
            cartItem.setOrder(order);
            cartItemDbService.saveCartItem(cartItem);
            order.getCartItems().add(cartItem);
        }
        return ResponseEntity.ok().build();
    }
}