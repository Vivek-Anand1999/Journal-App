package com.vivek.journal.JournalAPP.controller;

import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public_user")
public class HealthCheck {
    @Autowired
    private UserService userService;

    @GetMapping("/HealthCheck")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping
    public User createEntry(@RequestBody User user) {
        userService.saveUserWithEncryptedPassword(user);
        return user;
    }
}
/*
 * The whole endpoint look like localhost:8080/journalHealth/HealthCheck
 * */