package ru.aksenov.onlineshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Получение списка всех заказов.
     *
     * @return ResponseEntity со списком всех заказов.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Получение заказа по его идентификатору.
     *
     * @param orderId Идентификатор заказа.
     * @return ResponseEntity с заказом, если он найден.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    /**
     * Получение списка заказов по идентификатору пользователя.
     *
     * @param id Идентификатор пользователя.
     * @return ResponseEntity со списком заказов, принадлежащих указанному пользователю.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long id) {
        List<Order> orders = orderService.getOrderByUserId(id);
        return ResponseEntity.ok(orders);
    }

    /**
     * Создание нового заказа для указанного пользователя.
     *
     * @param userId Идентификатор пользователя, для которого создаётся заказ.
     * @return ResponseEntity с созданным заказом или сообщением об ошибке в случае неудачи.
     */
    @PostMapping("/create/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.createOrder(userId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
