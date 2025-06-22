package com.dimitriosliasis.employees.backend.service;

import com.dimitriosliasis.employees.backend.dto.ProjectOverlapDto;
import com.dimitriosliasis.employees.backend.dto.TopPair;
import com.dimitriosliasis.employees.backend.dto.UploadResponseDto;
import com.dimitriosliasis.employees.backend.model.EmployeeProjectRecord;
import com.dimitriosliasis.employees.backend.util.DateParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV‐driven implementation of CsvService.
 * Now supports multiple date formats through DateParser.
 */
@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public UploadResponseDto handleUpload(MultipartFile file) {

        List<EmployeeProjectRecord> records = parseRecords(file);

        List<ProjectOverlapDto> overlaps = OverlapCalculator.getProjectOverlaps(records);
        TopPair topPair = OverlapCalculator.findTopPair(overlaps);

        // Wrap into the response DTO
        return new UploadResponseDto(overlaps, topPair);
    }

    /**
     * Reads the uploaded CSV and returns one EmployeeProjectRecord per row.
     * Accepts multiple date formats via DateParser; "NULL" DateTo resolves to today.
     */
    private List<EmployeeProjectRecord> parseRecords(MultipartFile file) {
        List<EmployeeProjectRecord> result = new ArrayList<>();

        try (
                Reader in = new InputStreamReader(file.getInputStream());
                CSVParser parser = CSVParser.parse(
                        in,
                        CSVFormat.DEFAULT
                                .withFirstRecordAsHeader()
                                .withIgnoreHeaderCase()
                                .withTrim())
        ) {
            for (CSVRecord rec : parser) {
                int empId     = Integer.parseInt(rec.get("EmpID"));
                int projectId = Integer.parseInt(rec.get("ProjectID"));

                LocalDate from = DateParser.parse(rec.get("DateFrom"));

                String rawTo  = rec.get("DateTo");
                LocalDate to  = (rawTo == null
                        || rawTo.isBlank()
                        || rawTo.equalsIgnoreCase("NULL"))
                        ? LocalDate.now()
                        : DateParser.parse(rawTo);

                result.add(new EmployeeProjectRecord(empId, projectId, from, to));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read or parse CSV", e);
        }

        return result;
    }
}
