package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.CartStatus;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.repository.CartItemRepository;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartDbService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public Order saveCart(Order cart) {
        return cartRepository.save(cart);
    }

    public Order createOrder(Long cartId) throws Exception {
        Order order = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        order.setCartStatus(CartStatus.ORDER);

        return cartRepository.save(order);
    }
}
