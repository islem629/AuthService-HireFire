package com.iset.controller;

import com.iset.dto.AuthRequest;
import com.iset.dto.AuthResponse;
import com.iset.model.User;
import com.iset.repository.UserRepository;
import com.iset.service.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {

        System.out.println("REGISTER called with email = " + request.getEmail());

        // 1️⃣ Only check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("REGISTER ERROR: email already exists");
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // 2️⃣ Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        // 🔐 encode password (very important)
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(User.Role.USER);
        user.setEnabled(true);

        // 3️⃣ Save user (this will trigger INSERT)
        System.out.println("REGISTER: saving user in DB");
        userRepository.save(user);

        // 4️⃣ Generate token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail()));

    }


    // ------------------ LOGIN ------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), existingUser.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = existingUser.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail()));

    }

    // ------------------ HELPERS ------------------
    private boolean isPasswordStrong(String password) {
        // Minimum 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(pattern);
    }

    private boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}
