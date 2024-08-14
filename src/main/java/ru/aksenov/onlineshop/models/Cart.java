package ru.aksenov.onlineshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Cart")
@Getter
@Setter
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    // Вспомогательные методы

    /**
     * Добавление продукта в корзину.
     *
     * @param product Продукт, который нужно добавить.
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Удаление продукта из корзины.
     *
     * @param product Продукт, который нужно удалить.
     */
    public void removeProduct(Product product) {
        this.products.remove(product);
    }


    /**
     * Очистка корзины от всех продуктов.
     */
    public void clearProducts() {
        this.products.clear();
    }

    /**
     * Вычисление общей стоимости всех продуктов в корзине.
     *
     * @return Общая стоимость продуктов в корзине.
     */
    public double totalCost() {
        return products.stream().reduce(0d, (partialCost, product) -> partialCost + product.getCost(), Double::sum);
    }

    /**
     * Вычисление общей стоимости доступных для продажи продуктов в корзине.
     *
     * @return Общая стоимость доступных продуктов в корзине.
     */
    public double totalCostAvailable() {
        List<Product> availabeProducts = products.stream()
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.toList());
        return availabeProducts.stream().reduce(0d, (partialCost, product) -> partialCost + product.getCost(), Double::sum);
    }

    /**
     * Удаление из корзины всех продуктов, которые отсутствуют на складе.
     */
    public void removeOutOfStockProducts() {
        products = products.stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList());
    }

    /**
     * Удаление из корзины всех доступных для продажи продуктов.
     */
    public void removeAvailableProducts() {
        products = products.stream().filter(product -> product.getQuantity() == 0).collect(Collectors.toList());
    }
}
