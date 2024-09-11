package com.vivek.journal.JournalAPP.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 *! @Documents --> use to Map with Database
 * collection = "journal_entries" --> create db in our database
 *! @ Data --> THis Annotation used for Lombok for getter and setter method
 */
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JouranalEntity {
    @Id
    private ObjectId id;
    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;
}
