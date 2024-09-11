package com.vivek.journal.JournalAPP.controller;

/**
 * ? special type of class/components that handle HTTP Request
 * ? we will write here special endpoints as methods
 * ? controller will only create endpoints and call the services
 * <p>
 * * ------------------******----------------
 * ! GetAll -->localhost:8080/_journalEndpoints/{userName}
 * ! Create -->localhost:8080/_journalEndpoints/{userName}
 * ! GetById -->localhost:8080/_journalEndpoints/id/
 * ! Delete -->localhost:8080/_journalEndpoints/id/
 * ! update -->localhost:8080/_journalEndpoints/id/
 * * ------------------******----------------
 * * <?> --> WHILE CART PATTERN
 */
import com.vivek.journal.JournalAPP.entity.JouranalEntity;
import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.service.JournalEntryService;
import com.vivek.journal.JournalAPP.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByuserName(username);
        List<JouranalEntity> all = user.getUserJournalEntity();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JouranalEntity> createEntryOfUser(@RequestBody JouranalEntity myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JouranalEntity> getJournalEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByuserName(username);
        List<JouranalEntity> collect = user.getUserJournalEntity().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JouranalEntity> jouranalEntity = journalEntryService.getEntriesBtID(myId);
            if (jouranalEntity.isPresent()) {
                return new ResponseEntity<>(jouranalEntity.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.delete(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JouranalEntity> updateTheData(@PathVariable ObjectId id, @RequestBody JouranalEntity myUpdateEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByuserName(userName);
        List<JouranalEntity> collect = user.getUserJournalEntity().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JouranalEntity> jouranalEntity = journalEntryService.getEntriesBtID(id);
            if(jouranalEntity.isPresent()){
                JouranalEntity oldEntries = jouranalEntity.get();
                oldEntries.setTitle(myUpdateEntry.getTitle() != null && !myUpdateEntry.getTitle().equals("") ? myUpdateEntry.getTitle(): oldEntries.getTitle());
                oldEntries.setContent(myUpdateEntry.getContent() != null && !myUpdateEntry.getContent().equals("") ? myUpdateEntry.getContent() : oldEntries.getContent());
                journalEntryService.saveEntry(oldEntries);
                return new ResponseEntity<>(oldEntries, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
