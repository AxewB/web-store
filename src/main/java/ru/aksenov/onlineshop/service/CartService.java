package ru.aksenov.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aksenov.onlineshop.models.*;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        if (cart.getProducts().stream().anyMatch(item -> Objects.equals(item.getId(), product.getId()))) {
            throw new RuntimeException("This item is already in the cart");
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

    @Transactional
    public Order checkout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);



        // Вытягиваем доступные продукты
        List<Product> availableProducts = cart.getProducts().stream()
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.toList());
        if (availableProducts.isEmpty()) {
            throw new RuntimeException("There is no products in stock");
        }



        // Превращаем их в нужный обхъект для заказа
        List<OrderItem> orderItems = availableProducts.stream()
                .map(product -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(1);
                    orderItem.setOrder(order);

                    product.decreaseQuantity(1);
                    productRepository.save(product); // TODO: добавить возможность изменять количество покупаемого продукта
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        // Оставляем в корзине только те продукты, которых нет в наличии
        cart.setProducts(cart.getProducts().stream().filter(product -> !product.isAvailable(1)).collect(Collectors.toList()));
        cartRepository.save(cart);

        return order;

    }

    public double getTotalCostByCartId(Long id) {
        Cart cart = cartRepository.getCartById(id);
        return cart.totalCost();
    }
}
