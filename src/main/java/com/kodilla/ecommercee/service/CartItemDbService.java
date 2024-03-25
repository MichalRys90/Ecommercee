package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.CartItem;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.CartItemDto;
import com.kodilla.ecommercee.exception.CartItemDousntExist;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.CartItemMapper;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.repository.CartItemRepository;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartItemDbService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public List<CartItem> getProductsList(Long cartId) throws Exception {
        Order cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
                return cartMapper.mapToAllProducts(cartItemRepository.findAllByOrder(cart));
    }

    public Set<CartItemDto> cartItemList(Long cartId) throws Exception {
        Order cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        return cartItemMapper.mapToCartItemDtoList(cartItemRepository.findAllByOrder(cart));
    }

    public CartItem addProduct(Long cartId, Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Order cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        long count = 1;

        List<CartItem> cartItem = cartItemRepository.findAllByOrderAndProduct(cart, product);

        if (cartItem == null || cartItem.size() == 0) {
            return cartItemRepository.save(new CartItem(cart, product, product.getName(), product.getPrice(), 1, product.getPrice()));
        } else if (cartItem.size() == 1) {
            return getCartItemToAdd(product, cart);
        } else {
            for (CartItem item : cartItem) {
                count += item.getQuantity();
                cartItemRepository.deleteById(item.getId());
            }
            return cartItemRepository.save(new CartItem(cart, product, product.getName(), product.getPrice(), count, product.getPrice().multiply(new BigDecimal(String.valueOf(count)))));
        }
    }

    public void removeProduct(Long cartId, Long productId) throws Exception {
        Order cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        List<CartItem> cartItemList = cartItemRepository.findAllByOrderAndProduct(cart, product);
        if (cartItemList.size() == 0) {
            throw new CartItemDousntExist();
        } else if (cartItemList.size() == 1) {
            CartItem cartItem = cartItemRepository.findByOrderAndProduct(cart, product);
            if (cartItem.getQuantity() > 1) {
                getCartItemToRemove(product, cartItem);
            } else {
                cartItemRepository.deleteById(cartItem.getId());
            }
        } else {
            int count = -1;
            for (CartItem cartItem : cartItemList) {
                count += cartItem.getQuantity();
                cartItemRepository.deleteById(cartItem.getId());
            }
            cartItemRepository.save(new CartItem(cart, product, product.getName(), product.getPrice(), count, product.getPrice().multiply(new BigDecimal(String.valueOf(count)))));
        }
    }

    private CartItem getCartItemToAdd(Product product, Order cart) {
        CartItem cartItem1 = cartItemRepository.findByOrderAndProduct(cart, product);
        cartItem1.setQuantity(cartItem1.getQuantity() + 1);
        cartItem1.setTotalPrice(product.getPrice().multiply(new BigDecimal(String.valueOf(cartItem1.getQuantity()))));
        return cartItemRepository.save(cartItem1);
    }

    private void getCartItemToRemove(Product product, CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItem.setTotalPrice(product.getPrice().multiply(new BigDecimal(String.valueOf(cartItem.getQuantity()))));
        cartItemRepository.save(cartItem);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}
