package ru.aksenov.onlineshop.repository;

import ru.aksenov.onlineshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long aLong);
    // Метод для поиска продуктов по тексту
    List<Product> findByName(String name);

}
