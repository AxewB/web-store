package ru.aksenov.onlineshop.helperClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.aksenov.onlineshop.models.ERole;
import ru.aksenov.onlineshop.models.Role;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.RoleRepository;
import ru.aksenov.onlineshop.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            Role userRole = new Role(ERole.ROLE_USER);
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        if (!userRepository.existsByRoleName(ERole.ROLE_ADMIN)) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("There is no such role")));

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin12345"); // TODO: maybe encrypt it somehow
            adminUser.setEmail("admin@onlineshop.com");
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
        }

        if (categoryRepository.findAll().isEmpty()) {
//            ('Electronics', NULL);
//            ('Laptops', 1),
//            ('Smartphones', 1),
//            ('Home Appliances', NULL),
//            ('Refrigerators', 4),
//            ('Washing Machines', 4),
//            ('Books', NULL),
//            ('Fiction', 7),
//            ('Non-Fiction', 7),
//            ('Science Fiction', 8),
//            ('Biography', 9),
//            ('Children''s Books', 7),
//            ('Clothing', NULL),
//            ('Men''s Wear', 13),
//            ('Women''s Wear', 13),
//            ('Footwear', NULL),
//            ('Formal Shoes', 16),
//            ('Casual Shoes', 16),
//            ('Accessories', NULL),
//            ('Watches', 19),
//            ('Jewelry', 19);
//            ('Beauty', NULL),
//            ('Fragrances', NULL),
//            ('Furniture', NULL),
//            ('Groceries', NULL),
//            ('Home Decoration', NULL),
//            ('Kitchen Accessories', NULL),
//            ('Men''s Shirts', 14), -- Подкатегория к 'Men\'s Wear'
//            ('Men''s Shoes', 14); -- Подкатегория к 'Men\'s Wear'


        }

        if (productRepository.findAll().isEmpty()) {

        }
    }
}
