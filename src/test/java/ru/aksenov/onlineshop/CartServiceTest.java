package ru.aksenov.onlineshop;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.Order;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.OrderRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.UserRepository;
import ru.aksenov.onlineshop.service.CartService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;
    private Cart cart;

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
        product.setQuantity(10);
    }

    @Test
    void testAddProductToCart_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.addProductToCart(1L, 1L);

        assertTrue(cart.getProducts().contains(product));

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddProductToCart_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addProductToCart(1L, 1L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testAddProductToCart_productNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addProductToCart(1L, 1L));

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testAddProductToCart_itemAlreadyInCart() {
        cart.getProducts().add(product);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));


        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addProductToCart(1L, 1L));

        assertEquals("This item is already in the cart", exception.getMessage());
    }

    @Test
    void testRemoveProductFromCart_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        cartService.removeProductFromCart(1L, 1L);

        assertFalse(cart.getProducts().contains(product));

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveProductFromCart_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.removeProductFromCart(1L, 1L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testRemoveProductFromCart_productNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.removeProductFromCart(1L, 1L));

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testRemoveProductFromCart_cartNotFound() {
        user.setCart(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.removeProductFromCart(1L, 1L)
        );
        assertEquals("Cart not found", exception.getMessage());
    }


    @Test
    void testGetCartByUserId_returnExistingCart() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Cart resultCart = cartService.getCartByUserId(1L);

        assertEquals(resultCart, cart);
        verify(cartRepository, never()).save(cart);
    }


    @Test
    void testGetCartByUserId_returnNewCreatedCart() {
        user.setCart(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart resultCart = cartService.getCartByUserId(1L);

        assertNotNull(resultCart);
        assertEquals(user, cart.getUser());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetCartByUserId_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.getCartByUserId(1L)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCheckout_success() {
        cart.getProducts().add(product);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        Order order = cartService.checkout(1L);

        verify(productRepository, times(1)).save(product);
        verify(cartRepository, times(1)).save(cart);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCheckout_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.checkout(1L)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCheckout_cartIsEmpty() {
        user.setCart(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.checkout(1L)
        );

        assertEquals("Cart is empty", exception.getMessage());
    }

    @Test
    void testCheckout_noAvailableProducts() {
        product.setQuantity(0);
        user.getCart().getProducts().add(product);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.checkout(1L)
        );

        assertEquals("There is no products in stock", exception.getMessage());
    }

    @Test
    void testGetTotalCostByCartId_success() {
        product.setCost(100.0);
        cart.getProducts().add(product);

        when(cartRepository.getCartById(1L)).thenReturn(cart);

        double cost = cartService.getTotalCostByCartId(1L);

        assertEquals(100.0, cost);
    }


}
