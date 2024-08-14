package ru.aksenov.onlineshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.aksenov.onlineshop.models.ERole;
import ru.aksenov.onlineshop.models.Role;
import ru.aksenov.onlineshop.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);

  // Существует ли пользователь с определенной ролью
  Boolean existsByRoles(Role role);
}
