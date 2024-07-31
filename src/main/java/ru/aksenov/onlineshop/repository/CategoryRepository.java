package ru.aksenov.onlineshop.repository;

import org.springframework.stereotype.Repository;
import ru.aksenov.onlineshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long parentId);
    List<Category> findByParentIdIsNull();
}
