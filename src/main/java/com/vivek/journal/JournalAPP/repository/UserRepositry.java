package com.vivek.journal.JournalAPP.repository;

import com.vivek.journal.JournalAPP.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositry extends MongoRepository<User, ObjectId> {
    User findByuserName(String username);
    void deleteByUserName(String username);
}
