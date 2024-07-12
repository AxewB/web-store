package ru.aksenov.onlineshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long parentId;
    @NotBlank
    private String name;
}
