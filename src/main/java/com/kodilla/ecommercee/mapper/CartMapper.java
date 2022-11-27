package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.CartItem;
import com.kodilla.ecommercee.domain.CartStatus;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.domain.dto.CartItemDto;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMapper {

    public Order mapToCart(final CartDto cartDto) {
        return new Order(cartDto.getId(),
                null,
                LocalDateTime.now(),
                CartStatus.CART,
                new HashSet<>());
    }

    public Product mapCartItemToProduct(final CartItem cartItem) {
        return new Product(
                cartItem.getProduct().getId(),
                null,
                cartItem.getProduct().getName(),
                cartItem.getProduct().getDescription(),
                cartItem.getProduct().getPrice());
    }

    public List<Product> mapToProductList(final List<CartItem> productList) {
        return productList.stream()
                .map(this::mapCartItemToProduct)
                .collect(Collectors.toList());
    }

    public List<CartItem> mapToAllProducts(final List<CartItem> showAllProduct) {
        List<CartItem> cartItemList = new ArrayList<>();

        for (CartItem cartItem : showAllProduct) {
            long count = cartItem.getQuantity();
            while (count > 0) {
                cartItemList.add(new CartItem(1, cartItem.getOrder(), cartItem.getProduct(), cartItem.getProductName(),
                        cartItem.getUnitPrice(), 1, cartItem.getUnitPrice()));
                count--;
            }
        }
        return cartItemList;
    }
}
