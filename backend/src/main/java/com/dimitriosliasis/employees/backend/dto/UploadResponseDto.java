package com.dimitriosliasis.employees.backend.dto;

import java.util.List;

/**
 * The payload returned by POST /api/upload.
 * - records: list of all per-project overlaps
 * - topPair: the two employees with the largest aggregate days
 */
public class UploadResponseDto {
    private final List<ProjectOverlapDto> records;
    private final TopPair topPair;

    public UploadResponseDto(List<ProjectOverlapDto> records, TopPair topPair) {
        this.records = records;
        this.topPair = topPair;
    }

    public List<ProjectOverlapDto> getRecords() {
        return records;
    }

    public TopPair getTopPair() {
        return topPair;
    }

    @Override
    public String toString() {
        return "UploadResponseDto{" +
                "records=" + records +
                ", topPair=" + topPair +
                '}';
    }
}
