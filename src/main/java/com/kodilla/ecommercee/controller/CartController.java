package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/carts")
public class CartController {

    @Autowired
    private CartDbService cartDbService;
    @Autowired
    private OrderDbService orderDbService;
    @Autowired
    private CartItemDbService cartItemDbService;
    @Autowired
    private ActivityDbService activityDbService;
    @Autowired
    private UserDbService userDbService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @PostMapping(value = "/emptyCart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDto> createEmptyCart(@RequestBody CartDto cartDto) throws UserNotFoundException {
        activityDbService.saveActivity(new Activity(userDbService.getUser(cartDto.getUserId()),
                "Request create empty cart"));
        Order cart = cartMapper.mapToCart(cartDto);
        cartDbService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getCart/{cardId}")
    public ResponseEntity<List<ProductDto>> getCart(@PathVariable Long cardId) throws Exception {
        activityDbService.saveActivity(new Activity(orderDbService.getOrder(cardId).getUser(),
                "Request get cart with id: " + cardId));
        List<CartItem> products = cartItemDbService.getProductsList(cardId);
        return ResponseEntity.ok(productMapper.mapToProductDtoList(cartMapper.mapToProductList(products)));
    }

    @PutMapping(value = "/addProducts/{cartId}/{productId}")
    public ResponseEntity<ProductDto> addProduct(@PathVariable Long cartId, @PathVariable Long productId) throws Exception {
        activityDbService.saveActivity(new Activity(orderDbService.getOrder(cartId).getUser(),
                "Request add product to cart. Product id: " + productId));
        CartItem cartItem = cartItemDbService.addProduct(cartId, productId);
        return ResponseEntity.ok(productMapper.mapToProductDto(cartMapper.mapCartItemToProduct(cartItem)));
    }

    @DeleteMapping(value = "{cardId}/{productId}")
    public ResponseEntity<Void> deleteProductFromCard(@PathVariable Long cardId, @PathVariable Long productId) throws Exception {
        activityDbService.saveActivity(new Activity(orderDbService.getOrder(cardId).getUser(),
                "Request remove product from card: " + productId));
        cartItemDbService.removeProduct(cardId, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "createOrder/{cardId}")
    public ResponseEntity<Order> createOrderFromCart(@PathVariable Long cardId) throws Exception {
        activityDbService.saveActivity(new Activity(orderDbService.getOrder(cardId).getUser(),
                "Request create order from cart. Cart id: " + cardId));
        return ResponseEntity.ok(cartDbService.createOrder(cardId));
    }
}