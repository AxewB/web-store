package ru.aksenov.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aksenov.webshop.entity.Role;
import java.util.Optional;
public interface RoleRepo  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
