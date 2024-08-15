package ru.aksenov.onlineshop.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.aksenov.onlineshop.models.Cart;
import ru.aksenov.onlineshop.models.ERole;
import ru.aksenov.onlineshop.models.Role;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.payload.request.LoginRequest;
import ru.aksenov.onlineshop.payload.request.SignupRequest;
import ru.aksenov.onlineshop.payload.response.JwtResponse;
import ru.aksenov.onlineshop.payload.response.MessageResponse;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.RoleRepository;
import ru.aksenov.onlineshop.repository.UserRepository;
import ru.aksenov.onlineshop.security.jwt.JwtUtils;
import ru.aksenov.onlineshop.security.services.UserDetailsImpl;
import ru.aksenov.onlineshop.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResult = authService.authentication(loginRequest);
        return ResponseEntity.ok(jwtResult);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws InterruptedException {
        MessageResponse messageResponse = authService.registration(signUpRequest);

        if (messageResponse.getError()) {
            return ResponseEntity.badRequest().body(messageResponse);
        }
        else {
            return ResponseEntity.ok(messageResponse);
        }
    }
}
