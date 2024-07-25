package ru.aksenov.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aksenov.onlineshop.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
