
package com.libraryreminder.library_reminder.controller;

import com.libraryreminder.library_reminder.model.LibraryRecord;
import com.libraryreminder.library_reminder.service.CsvReaderService;
import com.libraryreminder.library_reminder.service.ReminderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ReminderController {

    private final CsvReaderService csvReaderService;
    private final ReminderService reminderService;

    public ReminderController(CsvReaderService csvReaderService,
                              ReminderService reminderService) {
        this.csvReaderService = csvReaderService;
        this.reminderService = reminderService;
    }

    @GetMapping("/reminders")
    public List<LibraryRecord> getReminderRecords() throws IOException {

        String filePath = "sample-data/library-report.csv";

        List<LibraryRecord> records =
                csvReaderService.readCsv(filePath);

        return reminderService.findUnreturnedBooks(records);
    }
}
