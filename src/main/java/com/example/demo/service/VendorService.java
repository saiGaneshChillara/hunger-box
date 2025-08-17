package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.security.JwtUtil;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse registerVendor(Vendor vendor) {
        if (vendorRepository.existsByEmail(vendor.getEmail())) {
            throw new RuntimeException("Vendor email already exists");
        }

        vendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
        Vendor savedVendor = vendorRepository.save(vendor);
        String token = jwtUtil.generateToken(savedVendor.getEmail(), savedVendor.getRole());

        return mapToResponse(savedVendor, token);
    }

    public AuthResponse loginVendor(String email, String password) {
        Vendor vendor = vendorRepository.findByEmail(email)
             .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(password, vendor.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(vendor.getEmail(), vendor.getRole());
        return mapToResponse(vendor, token);
    }

    public Optional<String> getVendorIdByEmail(String email) {
        Optional<Vendor> vendor = vendorRepository.findByEmail(email);
        if (vendor.isPresent()) {
            return Optional.of(vendor.get().getId());
        }

        return Optional.empty();
    }

    private AuthResponse mapToResponse(Vendor vendor, String token) {
        return new AuthResponse(vendor.getId(), vendor.getEmail(), vendor.getName(), token);
    }

}
