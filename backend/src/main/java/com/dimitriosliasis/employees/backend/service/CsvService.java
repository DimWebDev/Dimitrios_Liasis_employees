package com.dimitriosliasis.employees.backend.service;

import com.dimitriosliasis.employees.backend.dto.UploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Parses CSV and calculates overlaps.
 */
public interface CsvService {
    UploadResponseDto handleUpload(MultipartFile file);
}
