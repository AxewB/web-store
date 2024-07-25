package ru.aksenov.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aksenov.onlineshop.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
