package ru.aksenov.onlineshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.service.CartService;

@RestController
@RequestMapping("api/cart/")
public class CartController {

    @Autowired
    private CartService cartService;


    // Просмотр содержимого корзины
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Добавление продукта в заказ
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId, @RequestParam Long productId) {
        try {
            cartService.addProductToCart(userId, productId);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удаление продукта из корзины
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long userId, @RequestParam Long productId) {
        try {
            cartService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok("Product removed from cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Оформление заказа
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<Order> checkout(@PathVariable Long userId) {
        try {
            Order order = cartService.checkout(userId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
