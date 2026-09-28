package com.libraryreminder.library_reminder.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReminderEmail(String studentName,
                                  String email,
                                  String bookName,
                                  String dueDate) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Library Book Return Reminder");

        message.setText(
                "Dear " + studentName + ",\n\n" +
                "This is a reminder that the following library book " +
                "has not been returned:\n\n" +
                "Book: " + bookName + "\n" +
                "Due Date: " + dueDate + "\n\n" +
                "Please return the book to the library.\n\n" +
                "Thank you,\n" +
                "Library"
        );

        mailSender.send(message);
    }
}