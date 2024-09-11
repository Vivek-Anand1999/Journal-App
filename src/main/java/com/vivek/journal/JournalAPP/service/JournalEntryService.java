package com.vivek.journal.JournalAPP.service;

import com.vivek.journal.JournalAPP.entity.JouranalEntity;
import com.vivek.journal.JournalAPP.entity.User;
import com.vivek.journal.JournalAPP.repository.JournalEntryRepositry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepositry journalEntryRepositry;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JouranalEntity jouranalEntity, String userName) {
        try {
            User user = userService.findByuserName(userName); // we are finding user in this
            jouranalEntity.setDate(LocalDateTime.now()); // date time set
            JouranalEntity saved = journalEntryRepositry.save(jouranalEntity); // we are saving the updated journal
            // entry then
            user.getUserJournalEntity().add(saved); // taking that saved journal entry and put it into the list of user
            userService.saveEntry(user); // finally user save
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during saving", e);
        }

    }

    public void saveEntry(JouranalEntity jouranalEntity) {
        journalEntryRepositry.save(jouranalEntity);
    }

    public List<JouranalEntity> getAllEntries() {
        return journalEntryRepositry.findAll();
    }

    public Optional<JouranalEntity> getEntriesBtID(ObjectId id) {
        return journalEntryRepositry.findById(id);
    }

    @Transactional
    public void delete(ObjectId id, String userName) {
        try {
            User user = userService.findByuserName(userName);
            boolean removed = user.getUserJournalEntity().removeIf(x -> x.getId().equals(id));
            userService.saveEntry(user);
            journalEntryRepositry.deleteById(id);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("AN error occured during delete" + e);
        }

    }
}
// controller will call the service
// service will call repositry
// controller---> service --> repositry