package ru.aksenov.onlineshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.service.CartService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/cart/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Получение содержимого корзины и общей стоимости для указанного пользователя.
     *
     * @param userId Идентификатор пользователя, чью корзину нужно получить.
     * @return ResponseEntity, содержащий корзину и общую стоимость, или сообщение об ошибке в случае неудачи.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getCart(@PathVariable Long userId) {
        try {
            Map<String, Object> result = new HashMap<>();
            Cart cart = cartService.getCartByUserId(userId);
            result.put("cart", cart);
            result.put("totalCost", cartService.getTotalCostByCartId(cart.getId()));
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Добавление продукта в корзину для указанного пользователя.
     *
     * @param userId    Идентификатор пользователя, для которого добавляется продукт.
     * @param productId Идентификатор продукта, который нужно добавить в корзину.
     * @return ResponseEntity с подтверждением успешного добавления продукта или сообщение об ошибке.
     */
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId, @RequestParam Long productId) {
        try {
            cartService.addProductToCart(userId, productId);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Удаление продукта из корзины для указанного пользователя.
     *
     * @param userId    Идентификатор пользователя, для которого удаляется продукт.
     * @param productId Идентификатор продукта, который нужно удалить из корзины.
     * @return ResponseEntity с подтверждением успешного удаления продукта или сообщение об ошибке.
     */
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long userId, @RequestParam Long productId) {
        try {
            cartService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok("Product removed from cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Оформление заказа для указанного пользователя, включая создание заказа и очистку корзины.
     *
     * @param userId Идентификатор пользователя, для которого осуществляется оформление заказа.
     * @return ResponseEntity с деталями созданного заказа или сообщение об ошибке.
     */
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<Order> checkout(@PathVariable Long userId) {
        try {
            Order order = cartService.checkout(userId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Очистка корзины для указанного пользователя, удаление всех продуктов.
     *
     * @param userId Идентификатор пользователя, чью корзину нужно очистить.
     * @return ResponseEntity без содержимого, подтверждающее успешное выполнение операции.
     */
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        cart.clearProducts();
        return ResponseEntity.noContent().build();
    }
}
