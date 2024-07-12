package ru.aksenov.webshop.controllers;

//@RestController
//@RequestMapping("/api/auth")
public class AuthController {
//    private AuthenticationManager authenticationManager;
//    private UserRepo userRepo;
//    private RoleRepo roleRepo;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo,
//                          RoleRepo roleRepo, PasswordEncoder passwordEncoder){
//        this.authenticationManager = authenticationManager;
//        this.userRepo = userRepo;
//        this.roleRepo = roleRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostMapping("register")
//    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
//        if (userRepo.existsByUsername(registerDto.getUsername())){
//            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
//        }
//
//        User user = new User();
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
//
//        Role roles = roleRepo.findByName("USER").get();
//        user.setRole();
//    }
}
