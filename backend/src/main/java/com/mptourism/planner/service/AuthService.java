package com.mptourism.planner.service;

import com.mptourism.planner.dto.AuthResponse;
import com.mptourism.planner.dto.LoginRequest;
import com.mptourism.planner.dto.SignupRequest;
import com.mptourism.planner.model.User;
import com.mptourism.planner.repository.UserRepository;
import com.mptourism.planner.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse signup(SignupRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setWhatsapp(request.getWhatsapp());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail());

        AuthResponse response = new AuthResponse();
        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setEmail(saved.getEmail());
        response.setWhatsapp(saved.getWhatsapp());
        response.setToken(token);
        return response;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> optional = userRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = optional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setWhatsapp(user.getWhatsapp());
        response.setToken(token);
        return response;
    }
}
