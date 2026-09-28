package com.libraryreminder.library_reminder.model;

public class EmailResult {

    private String studentName;
    private String email;
    private String status;

    public EmailResult(String studentName,
                       String email,
                       String status) {

        this.studentName = studentName;
        this.email = email;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
