package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Building;
import com.example.demo.repository.BuildingRepository;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }
}
