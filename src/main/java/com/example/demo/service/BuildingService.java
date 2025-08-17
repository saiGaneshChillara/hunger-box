package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BuildingDetails;
import com.example.demo.model.Building;
import com.example.demo.model.Counter;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.CounterRepository;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private CounterRepository counterRepository;

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Optional<Building> getBuildingById(String id) {
        return buildingRepository.findById(id);
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }

    public List<BuildingDetails> getAllBuildingsWithCounters() {
        List<Building> buildings = buildingRepository.findAll();
        return buildings.stream().map(building -> {
            List<Counter> counters = counterRepository.findAllById(building.getCounters());
            return new BuildingDetails(
                building.getId(),
                building.getName(),
                building.getFloor(),
                building.getAddress(),
                counters
            );
        }).collect(Collectors.toList());
    }

    public BuildingDetails getBuildingWithCountersById(String buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(() -> new IllegalArgumentException("Invalid building id"));
        List<Counter> counters = counterRepository.findAllById(building.getCounters());

        return new BuildingDetails(
            building.getId(),
            building.getName(),
            building.getFloor(),
            building.getAddress(),
            counters
        );
    }
}
