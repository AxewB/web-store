package ru.aksenov.onlineshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.models.OrderItem;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.UserRepository;
import ru.aksenov.onlineshop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Cart cart;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        cart = new Cart();
        cart.setUser(user);
        cart.setProducts(new ArrayList<>());
        user.setCart(cart);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setQuantity(10);

        order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setId(1L);
    }

    @Test
    void createOrder_success() {
        cart.getProducts().add(product);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order newOrder = orderService.createOrder(1L);

        assertNotNull(newOrder);
        assertEquals(user, newOrder.getUser());
        assertEquals(1, newOrder.getOrderItems().size());
        assertEquals(product, newOrder.getOrderItems().get(0).getProduct());

        verify(userRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(newOrder);
        verify(productRepository, times(1)).save(product);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void createOrder_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(1L)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void createOrder_cartIsEmpty() {
        user.setCart(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(1L)
        );

        assertEquals("Cart is empty", exception.getMessage());
    }

    @Test
    void getAllOrders() {
        Order order = new Order() {{
            setOrderItems(List.of(new OrderItem() {{
                setId(1L);
            }}));
            setId(1L);
            setUser(user);
        }};

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(user, order.getUser());
        assertTrue(orders.contains(order));

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById() {
        when(orderRepository.getOrderById(1L)).thenReturn(order);

        Order newOrder = orderService.getOrderById(1L);

        assertNotNull(newOrder);
        assertEquals(order.getUser(), newOrder.getUser());
        assertEquals(order.getOrderDate(), newOrder.getOrderDate());

        verify(orderRepository, times(1)).getOrderById(1L);
    }

    @Test
    void getOrderByUserId() {
        when(orderRepository.getOrderByUserId(1L)).thenReturn(List.of(order));

        List<Order> orders = orderService.getOrderByUserId(1L);

        assertNotNull(orders);
        assertTrue(orders.contains(order));
        assertEquals(order.getUser(), orders.get(0).getUser());

        verify(orderRepository, times(1)).getOrderByUserId(1L);
    }
}
