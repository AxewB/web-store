package ru.aksenov.onlineshop.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cart")
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(
            name="cart_user",
            joinColumns = @JoinColumn(
                    name="user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="cart_id",
                    referencedColumnName = "id"
            )
    )
    @OneToOne(targetEntity = User.class)
    private List<User> users;


    @JoinTable(
            name="cart_product",
            joinColumns = @JoinColumn(
                    name="product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="cart_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany(targetEntity = Product.class)
    private List<Product> products;

}
