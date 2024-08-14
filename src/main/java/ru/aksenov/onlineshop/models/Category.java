package ru.aksenov.onlineshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Category> children = new ArrayList<>();

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    public Category() {}

    public Category(Long id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }
    public Category(Long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = null;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addChild(Category child) {
        child.setParent(this);
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                ", products=" + products +
                '}';
    }
}
