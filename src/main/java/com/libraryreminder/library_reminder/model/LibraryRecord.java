package com.libraryreminder.library_reminder.model;

public class LibraryRecord {

    private String studentId;
    private String studentName;
    private String email;
    private String bookName;
    private String dueDate;
    private String returned;

    public LibraryRecord(String studentId, String studentName, String email,
                         String bookName, String dueDate, String returned) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.bookName = bookName;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getBookName() {
        return bookName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReturned() {
        return returned;
    }
}
