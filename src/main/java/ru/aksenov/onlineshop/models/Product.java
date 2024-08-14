package ru.aksenov.onlineshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private List<String> images;
    private String thumbnail;
    private int quantity;
    private double cost;


    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Product() {
    }

    public Product(double cost, int quantity, List<String> images, String thumbnail, String description, String name) {
        this.cost = cost;
        this.quantity = quantity;
        this.thumbnail = thumbnail;
        this.images = images;
        this.description = description;
        this.name = name;
    }

    /**
     * Проверка наличия товара.
     *
     * @param requestedQuantity - Запрашиваемое количество товара.
     * @return true, если значение совпадает, иначе false.
     */
    public boolean isAvailable(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    /**
     * Уменьшение количества товара.
     * Используется при оформлении заказа.
     *
     * @param amount - Значение, на которое будет уменьшено количество товара.
     */
    public void decreaseQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
        }
    }

    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.description + " | " + this.quantity + " | " + this.cost;
    }
}
