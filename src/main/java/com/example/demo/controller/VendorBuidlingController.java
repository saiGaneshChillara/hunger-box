package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateCounterRequest;
import com.example.demo.model.Building;
import com.example.demo.model.Counter;
import com.example.demo.service.BuildingService;
import com.example.demo.service.CounterService;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("/api/secure/vendors")
public class VendorBuidlingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private CounterService counterService;

    @PostMapping("/buidlings")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> createBuilding(@RequestBody Building building) {
        Building createdBuilding = buildingService.createBuilding(building);
        return ResponseEntity.ok(createdBuilding);
    }

    @PostMapping("/counters/{buildingId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> createCounter(@PathVariable String buildingId, @RequestBody CreateCounterRequest request) {
        try {
            String vendorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            String vendorId = vendorService.getVendorIdByEmail(vendorEmail)
            .orElseThrow(() -> new IllegalArgumentException("Vendor not found or not logged in"));
            Counter savedCounter = counterService.createCounter(request.getName(), vendorId, buildingId);
            return ResponseEntity.ok(savedCounter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/counters/{counterId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCounter(@PathVariable String counterId) {
        try {
            String vendorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            counterService.deleteCounter(counterId, vendorEmail);
            return ResponseEntity.ok("Counter deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/counters/by-me")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> getMyCounters() {
        try {
            String vendorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            String vendorId = vendorService.getVendorIdByEmail(vendorEmail).orElseThrow(() -> new IllegalArgumentException("Vendor not found or not logged in"));
            List<Counter> counters = counterService.getCountersByVendorId(vendorId);
            return ResponseEntity.ok(counters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
