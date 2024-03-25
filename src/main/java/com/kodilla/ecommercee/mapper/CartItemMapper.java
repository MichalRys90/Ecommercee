package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.CartItem;
import com.kodilla.ecommercee.domain.dto.CartItemDto;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.service.OrderDbService;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartItemMapper {

    @Autowired
    private ProductDbService productDbService;
    @Autowired
    private OrderDbService orderDbService;

    public CartItemDto mapToCartItemDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getOrder().getId(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice(),
                cartItem.getProductName(),
                cartItem.getTotalPrice()
        );
    }


    public Set<CartItemDto> mapToCartItemDtoSet (Set<CartItem> cartItems) {
        Set<CartItemDto> cartItemDtoSet = new HashSet<>();
        for (CartItem cartItem: cartItems) {
            cartItemDtoSet.add(mapToCartItemDto(cartItem));
        }
        return cartItemDtoSet;
    }

    public Set<CartItemDto> mapToCartItemDtoList (List<CartItem> cartItems) {
        Set<CartItemDto> cartItemDtoSet = new HashSet<>();
        for (CartItem cartItem: cartItems) {
            cartItemDtoSet.add(mapToCartItemDto(cartItem));
        }
        return cartItemDtoSet;
    }

    public CartItem mapToCartItem(CartItemDto cartItemDto) throws ProductNotFoundException, OrderNotFoundException {
        return new CartItem(
                cartItemDto.getId(),
                orderDbService.getOrder(cartItemDto.getOrder_id()),
                productDbService.getProduct(cartItemDto.getProduct_id()),
                cartItemDto.getProductName(),
                cartItemDto.getUnitPrice(),
                cartItemDto.getQuantity(),
                cartItemDto.getTotalPrice()
        );
    }
}
