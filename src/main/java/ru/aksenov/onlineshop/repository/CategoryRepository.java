package ru.aksenov.onlineshop.repository;

import org.springframework.stereotype.Repository;
import ru.aksenov.onlineshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryById(Long id);
    List<Category> findByParentId(Long parentId);
    List<Category> findByParentIdIsNull();
}
