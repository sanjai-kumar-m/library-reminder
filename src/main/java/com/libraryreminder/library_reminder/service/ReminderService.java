package com.libraryreminder.library_reminder.service;

import com.libraryreminder.library_reminder.model.LibraryRecord;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderService {

    public List<LibraryRecord> findUnreturnedBooks(List<LibraryRecord> records) {

        List<LibraryRecord> reminderRecords = new ArrayList<>();

        for (LibraryRecord record : records) {

            if (record.getReturned().equalsIgnoreCase("No")) {
                reminderRecords.add(record);
            }
        }

        return reminderRecords;
    }
}