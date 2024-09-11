package com.vivek.journal.JournalAPP.controller;

import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser() {
        List<User> allUser = userService.getAllUsers();
        if (allUser != null && !allUser.isEmpty()) {
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-Admin-user")
    public void createAdminUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }
}
