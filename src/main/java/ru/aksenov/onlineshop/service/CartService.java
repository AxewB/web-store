package ru.aksenov.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aksenov.onlineshop.models.*;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void addProductToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        cart.removeProduct(product);
        cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCart();
    }

    public Order checkout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for (Product product : cart.getProducts()) {
            if (!product.isAvailable(1)) {
                throw new RuntimeException("Product " + product.getName() + " is out of stock");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(1); // Assuming each product is added once for simplicity

            product.decreaseQuantity(1);
            productRepository.save(product);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        cart.clearProducts();
        cartRepository.save(cart);

        return order;
    }
}
