package com.vivek.journal.JournalAPP.controller;

import com.vivek.journal.JournalAPP.api.response.WeatherResponse;
import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.repository.UserRepositry;
import com.vivek.journal.JournalAPP.service.UserService;
import com.vivek.journal.JournalAPP.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * ? special type of class/components that handle HTTP Request
 * ? we will write here special endpoints as methods
 * ? controller will only create endpoints and call the services
 * <p>
 * * ------------------******----------------
 * ! GetAll -->localhost:8080/user/see
 * ! Create -->localhost:8080/user/create
 * ! GetById -->localhost:8080/user/id/
 * ! Delete -->localhost:8080/user/id/
 * ! update -->localhost:8080/user/id/
 * * ------------------******----------------
 * * <?> --> WHILE CART PATTERN
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateEntryBtId(@RequestBody User newuserrData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User uerNameInDb = userService.findByuserName(username);
        uerNameInDb.setUserName(newuserrData.getUserName());
        uerNameInDb.setPassword(newuserrData.getPassword());
        userService.saveUserWithEncryptedPassword(uerNameInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepositry.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse != null){
            greeting = "waether feel like" + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("hii " + authentication.getName() + greeting, HttpStatus.OK);
    }
}
/**
 * ! Public controller
 * ***************************
 *
 * @GetMapping public List<User> getAllEntriess(){
 * return userService.getAllEntries();
 * }
 */
