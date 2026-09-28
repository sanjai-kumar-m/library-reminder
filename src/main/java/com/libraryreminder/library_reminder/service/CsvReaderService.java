package com.libraryreminder.library_reminder.service;

import com.libraryreminder.library_reminder.model.LibraryRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    public List<LibraryRecord> readCsv(String filePath) throws IOException {

        List<LibraryRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            // Skip the header row
            reader.readLine();

            int rowNumber = 1;

            while ((line = reader.readLine()) != null) {

                rowNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != 6) {
                    throw new IllegalArgumentException(
                            "Invalid CSV row at line " + rowNumber +
                            ". Expected 6 columns but found " + data.length
                    );
                }

                LibraryRecord record = new LibraryRecord(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim()
                );

                records.add(record);
            }
        }

        return records;
    }

    public List<LibraryRecord> readCsv(MultipartFile file) throws IOException {

        List<LibraryRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;

            // Skip the header row
            reader.readLine();

            int rowNumber = 1;

            while ((line = reader.readLine()) != null) {

                rowNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                    if (data.length != 6) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Expected 6 columns but found " + data.length
                        );
                    }

                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim();
                    }

                    if (data[0].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Student ID is empty."
                        );
                    }

                    if (data[1].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Student Name is empty."
                        );
                    }

                    if (data[2].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Email is empty."
                        );
                    }


                    if (!data[2].matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Invalid email format: " + data[2]
                        );
                    }

                    if (data[3].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Book Name is empty."
                        );
                    }

                    if (data[4].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Due Date is empty."
                        );
                    }

                    if (data[5].isBlank()) {
                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Returned value is empty."
                        );
                    }

                    if (!data[5].equalsIgnoreCase("Yes") &&
                        !data[5].equalsIgnoreCase("No")) {

                        throw new IllegalArgumentException(
                                "Invalid CSV row at line " + rowNumber +
                                ". Returned must be 'Yes' or 'No'."
                        );
                    }

                    LibraryRecord record = new LibraryRecord(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5]
                    );

                    records.add(record);
            }
            if (records.isEmpty()) {
                throw new IllegalArgumentException(
                        "The uploaded CSV file contains no student records."
                );
            }
        }

        return records;
    }
}