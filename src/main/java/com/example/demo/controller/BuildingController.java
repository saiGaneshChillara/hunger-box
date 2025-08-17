package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Building;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.service.BuildingService;

@RestController
@RequestMapping("/api")
public class BuildingController {
    private final BuildingRepository buildingRepository;
    private final BuildingService buildingService;

    public BuildingController(BuildingRepository buildingRepository, BuildingService buildingService) {
        this.buildingRepository = buildingRepository;
        this.buildingService = buildingService;
    }

    @GetMapping("/buildings")
    public ResponseEntity<List<Building>> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();
        return ResponseEntity.ok(buildings);
    }

    @GetMapping("/buildings/with-counters")
    public ResponseEntity<?> getAllBuildingsWithCounters() {
        try {
            return ResponseEntity.ok(buildingService.getAllBuildingsWithCounters());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/buildings/with-counters/{buildingId}")
    public ResponseEntity<?> getBuildingWithCounterById(@PathVariable String buildingId) {
        try {
            return ResponseEntity.ok(buildingService.getBuildingWithCountersById(buildingId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
