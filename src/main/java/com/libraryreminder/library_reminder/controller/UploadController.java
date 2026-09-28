package com.libraryreminder.library_reminder.controller;

import com.libraryreminder.library_reminder.model.EmailResult;
import com.libraryreminder.library_reminder.model.LibraryRecord;
import com.libraryreminder.library_reminder.service.CsvReaderService;
import com.libraryreminder.library_reminder.service.ReminderService;
import com.libraryreminder.library_reminder.service.EmailService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {

    private final CsvReaderService csvReaderService;
    private final ReminderService reminderService;
    private final EmailService emailService;

    public UploadController(CsvReaderService csvReaderService,
                            ReminderService reminderService,
                            EmailService emailService) {

        this.csvReaderService = csvReaderService;
        this.reminderService = reminderService;
        this.emailService = emailService;
    }

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            Model model) {

        try {

                 if (file.isEmpty()) {
                    throw new IllegalArgumentException(
                            "No file was selected."
                    );
                }

                String fileName = file.getOriginalFilename();

                if (fileName == null ||
                    !fileName.toLowerCase().endsWith(".csv")) {

                    throw new IllegalArgumentException(
                            "Invalid file type. Please upload a CSV file."
                    );
                }

            List<LibraryRecord> records =
                    csvReaderService.readCsv(file);

            List<LibraryRecord> reminderRecords =
                    reminderService.findUnreturnedBooks(records);

            model.addAttribute("reminderRecords", reminderRecords);

            List<EmailResult> emailResults = new ArrayList<>();

            for (LibraryRecord record : reminderRecords) {

                try {

                    emailService.sendReminderEmail(
                            record.getStudentName(),
                            record.getEmail(),
                            record.getBookName(),
                            record.getDueDate()
                    );

                    emailResults.add(
                            new EmailResult(
                                    record.getStudentName(),
                                    record.getEmail(),
                                    "SENT"
                            )
                    );

                } catch (Exception e) {

                    emailResults.add(
                            new EmailResult(
                                    record.getStudentName(),
                                    record.getEmail(),
                                    "FAILED"
                            )
                    );

                    System.out.println(
                            "Failed to send email to: " +
                            record.getEmail()
                    );

                    System.out.println(
                            "Error: " + e.getMessage()
                    );
                }
            }

            model.addAttribute("emailResults", emailResults);

            return "result";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            return "upload-error";

        } catch (IOException e) {

            model.addAttribute(
                    "errorMessage",
                    "Unable to read the uploaded file."
            );

            return "upload-error";
        }
    }
}