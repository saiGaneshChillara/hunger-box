package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "counters")
public class Counter {
    @Id
    private String id;
    private String name;
    @Field("vendor")
    private String vendorId;
    @Field("building")
    private String buildingId;
}
