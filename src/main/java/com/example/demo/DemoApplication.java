package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

  @Value("${NAME:World}")
  String name;

  @RestController
  class HelloworldController {
    @GetMapping("/")
    String hello() {
      return "Hello " + name + "!";
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner initIndexes(MongoTemplate mongoTemplate) {
    return args -> {
      IndexOperations indexOps = mongoTemplate.indexOps("users");
      indexOps.ensureIndex(new Index().on("email", org.springframework.data.domain.Sort.Direction.ASC).unique());
    };
  }

}
