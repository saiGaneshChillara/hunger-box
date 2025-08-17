package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "buildings")
public class Building {
    @Id
    private String id;
    private String name;
    private int floor;
    private String address;
    private List<String> counters = new ArrayList<>();

    public void addCounter(String counterId) {
        this.counters.add(counterId);
    }
}
