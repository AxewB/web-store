package ru.aksenov.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.models.OrderItem;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    public Order createOrder(Long userId) {
        Cart cart = cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
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

        // Clear the cart after order is created
        cart.getProducts().clear();
        cartRepository.save(cart);

        return order;
    }
}
