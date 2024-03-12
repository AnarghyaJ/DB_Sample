package com.example.db_sample.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
@ComponentScan("service")
public class DataLoader implements CommandLineRunner {
    private final UserService service;

    @Autowired
    DataLoader(UserService service){
        this.service = service;
    }
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        service.saveUser(user);

        user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        service.saveUser(user);

    }
}
