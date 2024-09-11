package com.vivek.journal.JournalAPP.repository;

import com.vivek.journal.JournalAPP.entity.JouranalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepositry extends MongoRepository<JouranalEntity, ObjectId> {
}
