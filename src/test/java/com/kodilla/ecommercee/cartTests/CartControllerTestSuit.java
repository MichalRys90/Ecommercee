package com.kodilla.ecommercee.cartTests;

import com.kodilla.ecommercee.controller.CartController;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartItemRepository;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.service.CartDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CartControllerTestSuit {

    @Autowired
    private CartDbService cartDbService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartController cartController;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void testCreateEmptyCart() throws UserNotFoundException {
        //Given
        User user = new User();
        userRepository.save(user);
        CartDto cartDto = new CartDto(1, user.getUserId());

        //When
        ResponseEntity<CartDto> createCart = cartController.createEmptyCart(cartDto);

        //Then
        assertEquals(200, createCart.getStatusCode().value());
    }

    @Test
    void testGetCart() throws Exception {
        //Given
        User user = new User("Name", "First name", "Last name", false, "2L", new ArrayList<>());
        userRepository.save(user);
        Order cart = new Order(user, LocalDateTime.now(), CartStatus.CART, new HashSet<>());
        cartDbService.saveCart(cart);
        Group group = new Group("group name", new ArrayList<>());
        groupRepository.save(group);
        Product product = new Product("Name", "Description", new BigDecimal(10), new HashSet<>(), group);
        Product product1 = new Product("Name1", "Description1", new BigDecimal(8), new HashSet<>(), group);
        Product product2 = new Product("Name2", "Description2", new BigDecimal(15), new HashSet<>(), group);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        cartController.addProduct(cart.getId(), product.getId());
        cartController.addProduct(cart.getId(), product1.getId());
        cartController.addProduct(cart.getId(), product2.getId());

        //When
        ResponseEntity<List<ProductDto>> getCart = cartController.getCart(cart.getId());

        //Then
        assertEquals(200, getCart.getStatusCode().value());
        assertEquals(3, Objects.requireNonNull(getCart.getBody()).size());
    }

    @Test
    void testAddProduct() throws Exception {
        //Given
        User user = new User("Name", "First name", "Last name", false, "2L", new ArrayList<>());
        userRepository.save(user);
        Order cart = new Order(user, LocalDateTime.now(), CartStatus.CART, new HashSet<>());
        cartDbService.saveCart(cart);
        Group group = new Group("group name", new ArrayList<>());
        groupRepository.save(group);
        Product product = new Product("Name", "Description", new BigDecimal(10), new HashSet<>(), group);
        Product product1 = new Product("Name1", "Description1", new BigDecimal(8), new HashSet<>(), group);
        Product product2 = new Product("Name2", "Description2", new BigDecimal(15), new HashSet<>(), group);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        ResponseEntity<ProductDto> addProduct = cartController.addProduct(cart.getId(), product1.getId());

        //Then
        assertEquals(200, addProduct.getStatusCode().value());
        assertEquals("Description1", Objects.requireNonNull(addProduct.getBody()).getDescription());
    }

    @Test
    void testDeleteProductFromCart() throws Exception {
        //Given
        User user = new User("Name", "First name", "Last name", false, "2L", new ArrayList<>());
        userRepository.save(user);
        Order cart = new Order(user, LocalDateTime.now(), CartStatus.CART, new HashSet<>());
        cartDbService.saveCart(cart);
        Group group = new Group("group name", new ArrayList<>());
        groupRepository.save(group);
        Product product = new Product("Name", "Description", new BigDecimal(10), new HashSet<>(), group);
        Product product1 = new Product("Name1", "Description1", new BigDecimal(8), new HashSet<>(), group);
        Product product2 = new Product("Name2", "Description2", new BigDecimal(15), new HashSet<>(), group);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        cartController.addProduct(cart.getId(), product.getId());
        cartController.addProduct(cart.getId(), product1.getId());
        cartController.addProduct(cart.getId(), product2.getId());

        //When
        ResponseEntity<List<ProductDto>> getCart1 = cartController.getCart(cart.getId());
        ResponseEntity<Void> removeFromCart = cartController.deleteProductFromCard(cart.getId(), product.getId());
        ResponseEntity<List<ProductDto>> getCart = cartController.getCart(cart.getId());
        for (ProductDto productDto : Objects.requireNonNull(getCart1.getBody())) {
            System.out.println(productDto);
        }
        System.out.println();
        for (ProductDto productDto : Objects.requireNonNull(getCart.getBody())) {
            System.out.println(productDto);
        }

        //Then
        assertEquals(200, removeFromCart.getStatusCode().value());
        assertEquals(3, Objects.requireNonNull(getCart1.getBody()).size());
        assertEquals(2, Objects.requireNonNull(getCart.getBody()).size());
    }

    @Test
    void testCreateOrder() throws Exception {
        //Given
        User user = new User("Name", "First name", "Last name", false, "2L", new ArrayList<>());
        userRepository.save(user);
        Order cart = new Order(user, LocalDateTime.now(), CartStatus.CART, new HashSet<>());
        cartDbService.saveCart(cart);
        Group group = new Group("group name", new ArrayList<>());
        groupRepository.save(group);
        Product product = new Product("Name", "Description", new BigDecimal(10), new HashSet<>(), group);
        Product product1 = new Product("Name1", "Description1", new BigDecimal(8), new HashSet<>(), group);
        Product product2 = new Product("Name2", "Description2", new BigDecimal(15), new HashSet<>(), group);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        cartController.addProduct(cart.getId(), product.getId());
        cartController.addProduct(cart.getId(), product1.getId());
        cartController.addProduct(cart.getId(), product2.getId());
        cartController.addProduct(cart.getId(), product2.getId());

        //When

        ResponseEntity<Order> createOrder = cartController.createOrderFromCart(cart.getId());
        Set<CartItem> set = new HashSet<>(cartItemRepository.findAllByOrder(cart));

        for (CartItem cartItem : set) {
            System.out.println(cartItem);
        }

        //Then
        assertEquals(200, createOrder.getStatusCode().value());
        assertEquals(CartStatus.ORDER, Objects.requireNonNull(createOrder.getBody()).getCartStatus());

        assertEquals(3, set.size());
    }
}