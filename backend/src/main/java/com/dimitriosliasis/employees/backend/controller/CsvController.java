package com.dimitriosliasis.employees.backend.controller;

import com.dimitriosliasis.employees.backend.dto.UploadResponseDto;
import com.dimitriosliasis.employees.backend.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * Accepts a CSV file and returns overlap information.
 */
@RestController
@RequestMapping("/api")
public class CsvController {

    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024; // 5 MB
    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadResponseDto> upload(@RequestParam("file") MultipartFile file) {
        validateFile(file);
        UploadResponseDto dto = csvService.handleUpload(file);
        return ResponseEntity.ok(dto);
    }

    /* ---------- helpers ---------- */
    private void validateFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null || !name.toLowerCase().endsWith(".csv")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only .csv files are accepted.");
        }
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File exceeds 5 MB limit.");
        }
        String type = file.getContentType();
        if (type != null && !type.startsWith("text/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid MIME type: " + type);
        }
    }
}
