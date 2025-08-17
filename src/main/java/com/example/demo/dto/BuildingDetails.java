package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Counter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDetails {
    private String id;
    private String name;
    private int floor;
    private String address;
    private List<Counter> counters;
}
