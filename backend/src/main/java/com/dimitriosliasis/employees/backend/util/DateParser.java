package com.dimitriosliasis.employees.backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Parses dates in several common formats.
 */
public final class DateParser {

    // Ordered from most- to least-common
    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE,            // 2024-06-22
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),    // 06/22/2024
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),    // 22.06.2024
            DateTimeFormatter.ISO_DATE                   // 2024-06-22T00:00:00Z, etc.
    );

    private DateParser() {}  // static-only

    /**
     * Try each formatter in turn until one succeeds.
     * @throws DateTimeParseException if none match
     */
    public static LocalDate parse(String raw) {
        DateTimeParseException last = null;
        for (DateTimeFormatter f : FORMATTERS) {
            try {
                return LocalDate.parse(raw, f);
            } catch (DateTimeParseException ex) {
                last = ex;   // keep last error to re-throw if all fail
            }
        }
        throw last;
    }
}
