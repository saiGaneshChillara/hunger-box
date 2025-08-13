package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Building;
import com.example.demo.service.BuildingService;

@RestController
@RequestMapping("/api/secure/vendors")
public class VendorBuidlingController {
    @Autowired
    private BuildingService buildingService;

    @PostMapping("/buidlings")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Building> createBuilding(@RequestBody Building building) {
        Building createdBuilding = buildingService.createBuilding(building);
        return ResponseEntity.ok(createdBuilding);
    }
}
