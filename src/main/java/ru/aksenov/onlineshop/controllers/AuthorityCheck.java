package ru.aksenov.onlineshop.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(name = "api/authority")
public class AuthorityCheck {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean adminAccess() {
        return true;
    }
}
