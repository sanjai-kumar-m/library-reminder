# Library Reminder System

An automated library due reminder and email notification system built using Java and Spring Boot.

## Problem

Library staff may need to manually contact many students whose borrowed books have not been returned.

This project automates that process by allowing library staff to upload a CSV report, identify students with unreturned books, and send personalized email reminders.

## Phase 1 Objective

The current Phase 1 implementation provides this workflow:

```text
Upload CSV
    ↓
Validate CSV data
    ↓
Read library records
    ↓
Find unreturned books
    ↓
Display reminder students
    ↓
Send personalized emails
    ↓
Show SENT / FAILED status