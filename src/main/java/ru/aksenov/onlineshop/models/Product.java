package ru.aksenov.onlineshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private List<String> images;
    private String thumbnail;
    @NotBlank
    private Integer quantity;
    @NotBlank
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
    @NotBlank
    @ManyToMany
    private Set<Category> category;

    public Product() {}

    public Product (String name, String description, Integer quantity, Float cost, List<String> images, String thumbnail) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
        this.images = images;
        this.thumbnail = thumbnail;
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

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.description+ " | " + this.quantity + " | " + this.cost;
    }
}
