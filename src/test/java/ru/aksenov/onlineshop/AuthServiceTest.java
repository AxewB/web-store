package ru.aksenov.onlineshop;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.aksenov.onlineshop.repository.CartRepository;
import ru.aksenov.onlineshop.repository.RoleRepository;
import ru.aksenov.onlineshop.repository.UserRepository;
import ru.aksenov.onlineshop.security.jwt.JwtUtils;
import ru.aksenov.onlineshop.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    AuthService authService;

}
