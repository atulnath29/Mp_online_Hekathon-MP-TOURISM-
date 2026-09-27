package com.mptourism.planner.service;

import com.mptourism.planner.config.JwtUtil;
import com.mptourism.planner.dto.*;
import com.mptourism.planner.model.User;
import com.mptourism.planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (request.getLastName() == null || request.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (request.getEmail() == null || request.getEmail().isBlank() || !request.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email address is required.");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Email is already registered. Please login.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
            request.getFirstName().trim(),
            request.getLastName().trim(),
            request.getEmail().trim().toLowerCase(),
            request.getWhatsapp() != null ? request.getWhatsapp().trim() : "",
            encodedPassword,
            null
        );

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId());

        return new AuthResponse(
            token,
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail(),
            savedUser.getWhatsapp(),
            savedUser.getProfileImage(),
            "Registration successful! Welcome to MP Tourism."
        );
    }

    public AuthResponse login(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String email = request.getEmail().trim().toLowerCase();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        return new AuthResponse(
            token,
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getWhatsapp(),
            user.getProfileImage(),
            "Login successful! Welcome back, " + user.getFirstName()
        );
    }

    public User getUserFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Unauthorized token.");
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired session token.");
        }
        String email = jwtUtil.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public AuthResponse updateProfile(String authHeader, UpdateProfileRequest request) {
        User user = getUserFromToken(authHeader);
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName().trim());
        }
        if (request.getWhatsapp() != null) {
            user.setWhatsapp(request.getWhatsapp().trim());
        }

        User savedUser = userRepository.save(user);
        String token = authHeader.substring(7);

        return new AuthResponse(
            token,
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail(),
            savedUser.getWhatsapp(),
            savedUser.getProfileImage(),
            "Profile updated successfully!"
        );
    }

    public AuthResponse updateAvatar(String authHeader, AvatarRequest request) {
        User user = getUserFromToken(authHeader);
        if (request.getImageBase64() == null || request.getImageBase64().isBlank()) {
            throw new IllegalArgumentException("Image data is required.");
        }
        user.setProfileImage(request.getImageBase64());
        User savedUser = userRepository.save(user);
        String token = authHeader.substring(7);

        return new AuthResponse(
            token,
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail(),
            savedUser.getWhatsapp(),
            savedUser.getProfileImage(),
            "Profile avatar updated!"
        );
    }
}
