package com.vivek.journal.JournalAPP.service;

import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.repository.UserRepositry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepositry userRepositry;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);// for logger


    public void saveEntry(User user){
        userRepositry.save(user);
    }

    public boolean saveUserWithEncryptedPassword(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepositry.save(user);
            return true;
        }catch(Exception e){
            log.warn("duplicate user are not allowed"); // for logger and we need to use
            return  false;
        }

    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepositry.save(user);
    }

    public List<User> getAllUsers(){
        return userRepositry.findAll();
    }

    public Optional<User> getEntriesBtID(ObjectId id){
        return userRepositry.findById(id);
    }

    public void deleteee(ObjectId id){
        userRepositry.deleteById(id);
    }

    public User findByuserName(String username){
        return userRepositry.findByuserName(username);
    }

    public void deleteByUser(String user){
        userRepositry.deleteByUserName(user);
    }
}
// controller will call the service
// service will call repositry
// controller---> service --> repositry