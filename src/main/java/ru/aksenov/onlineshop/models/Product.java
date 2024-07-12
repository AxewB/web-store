package ru.aksenov.onlineshop.models;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Float cost;

    @JoinTable(
            joinColumns = @JoinColumn(
                    name="product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="category_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany
    private Set<Category> category;

    public Product() {}

    public Product (String name, String description, Integer quantity, Float cost) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return this.cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.description+ " | " + this.quantity + " | " + this.cost;
    }

}
