package ru.aksenov.onlineshop.repository;

import org.springframework.stereotype.Repository;
import ru.aksenov.onlineshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
