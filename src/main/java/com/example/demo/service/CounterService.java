package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Building;
import com.example.demo.model.Counter;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.CounterRepository;

@Service
public class CounterService {
    private final CounterRepository counterRepository;
    private final BuildingRepository buildingRepository;
    private final VendorService vendorService;

    public CounterService(CounterRepository counterRepository, BuildingRepository buildingRepository, VendorService vendorService) {
        this.counterRepository = counterRepository;
        this.buildingRepository = buildingRepository;
        this.vendorService = vendorService;
    }

    public Counter createCounter(String name, String vendorId, String buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(() -> new IllegalArgumentException("Invalid building id"));

        Counter counter = new Counter(null, name, vendorId, buildingId);
        Counter savedCounter = counterRepository.save(counter);

        building.addCounter(savedCounter.getId());
        buildingRepository.save(building);

        return savedCounter;
    }

    public void deleteCounter(String counterId, String vendorEmail) {
        Counter counter = counterRepository.findById(counterId).orElseThrow(() -> new IllegalArgumentException("Counter not found or invalid counterId"));
        String vendorId = vendorService.getVendorIdByEmail(vendorEmail).orElseThrow(() -> new IllegalArgumentException("Vendor not found or invalid vendor id"));
        if (!counter.getVendorId().equals(vendorId)) {
            throw new IllegalArgumentException("Not authorized to delete this counter, you are not the owner");
        }

        Building building = buildingRepository.findById(counter.getBuildingId()).orElseThrow(() -> new IllegalArgumentException("Building not found"));
        building.getCounters().remove(counterId);
        buildingRepository.save(building);
        counterRepository.deleteById(counterId);
    }

    public List<Counter> getCountersByVendorId(String vendorId) {
        return counterRepository.findByVendorId(vendorId);
    }
}
