package ru.aksenov.onlineshop.repository;

import ru.aksenov.onlineshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
