package ru.aksenov.onlineshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aksenov.onlineshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long aLong);

    // Поиск продуктов по множеству идентификаторов категорий
    List<Product> findByCategoriesIdIn(Set<Long> categoryIds);

    // Приск продуктов по имени
    List<Product> findByName(String name);

    // Поиск продукта, игнорируя регистр и пробелы
    @Query(
            value = """
                    SELECT *
                    FROM product
                    WHERE LOWER(REPLACE(name, ' ', ''))
                    LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))
                """,
            nativeQuery = true)
    List<Product> findByNameIgnoreCaseAndSpaces(@Param("name") String name);
}
