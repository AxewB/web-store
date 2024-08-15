package ru.aksenov.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.models.OrderItem;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.UserRepository;

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

    @Autowired
    private UserRepository userRepository;

    /**
     * Получение списка всех заказов.
     *
     * @return Список всех заказов.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Получение заказа по его идентификатору.
     *
     * @param orderId Идентификатор заказа.
     * @return Заказ с указанным идентификатором.
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    /**
     * Получение списка заказов для указанного пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Список заказов, принадлежащих пользователю.
     */
    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    /**
     * Создание нового заказа на основе содержимого корзины пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Созданный заказ.
     * @throws RuntimeException Если корзина не найдена или пуста, или если какой-либо продукт не доступен.
     */
    @Transactional
    public Order createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart == null || cart.getProducts().isEmpty()) {
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
