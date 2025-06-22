package com.dimitriosliasis.employees.backend.service;

import com.dimitriosliasis.employees.backend.dto.ProjectOverlapDto;
import com.dimitriosliasis.employees.backend.dto.TopPair;
import com.dimitriosliasis.employees.backend.model.EmployeeProjectRecord;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Computes per-project overlaps and aggregates totals per employee pair.
 */
public class OverlapCalculator {

    /**
     * Step 1 & 2: Group by project, then compute each pair’s overlap days.
     */
    public static List<ProjectOverlapDto> getProjectOverlaps(List<EmployeeProjectRecord> records) {
        // Group records by projectId
        Map<Integer, List<EmployeeProjectRecord>> byProject = new HashMap<>();
        for (EmployeeProjectRecord r : records) {
            byProject
                    .computeIfAbsent(r.getProjectId(), k -> new ArrayList<>())
                    .add(r);
        }

        List<ProjectOverlapDto> overlaps = new ArrayList<>();

        // For each project’s list, compare every unordered pair
        for (Map.Entry<Integer, List<EmployeeProjectRecord>> entry : byProject.entrySet()) {
            int projectId = entry.getKey();
            List<EmployeeProjectRecord> list = entry.getValue();
            int n = list.size();

            for (int i = 0; i < n; i++) {
                EmployeeProjectRecord r1 = list.get(i);
                for (int j = i + 1; j < n; j++) {
                    EmployeeProjectRecord r2 = list.get(j);

                    // Normalize employee IDs so emp1 < emp2
                    int emp1 = Math.min(r1.getEmpId(), r2.getEmpId());
                    int emp2 = Math.max(r1.getEmpId(), r2.getEmpId());

                    // Compute overlap range
                    LocalDate start = r1.getDateFrom().isAfter(r2.getDateFrom())
                            ? r1.getDateFrom()
                            : r2.getDateFrom();
                    LocalDate end   = r1.getDateTo().isBefore(r2.getDateTo())
                            ? r1.getDateTo()
                            : r2.getDateTo();

                    long days = ChronoUnit.DAYS.between(start, end) + 1;
                    if (days > 0) {
                        overlaps.add(new ProjectOverlapDto(emp1, emp2, projectId, days));
                    }
                }
            }
        }

        return overlaps;
    }

    /**
     * Step 3 & 4: Aggregate per-pair total days and pick the top pair (tie → lowest IDs).
     */
    public static TopPair findTopPair(List<ProjectOverlapDto> overlaps) {
        // Aggregate total days per (emp1, emp2)
        Map<Pair, Long> totalDays = new HashMap<>();
        for (ProjectOverlapDto dto : overlaps) {
            Pair key = new Pair(dto.getEmp1(), dto.getEmp2());
            totalDays.put(key, totalDays.getOrDefault(key, 0L) + dto.getDays());
        }

        // Find the maximum; on ties pick the lexicographically smaller pair
        Pair best = null;
        long maxDays = 0;
        for (Map.Entry<Pair, Long> e : totalDays.entrySet()) {
            Pair p = e.getKey();
            long d = e.getValue();
            if (best == null
                    || d > maxDays
                    || (d == maxDays && p.compareTo(best) < 0)) {
                best = p;
                maxDays = d;
            }
        }

        if (best == null) {
            // no overlaps at all
            return new TopPair(0, 0, 0L);
        }
        return new TopPair(best.emp1, best.emp2, maxDays);
    }

    /**
     * Simple key for employee pairs, with natural ordering.
     */
    private static class Pair implements Comparable<Pair> {
        final int emp1, emp2;
        Pair(int emp1, int emp2) {
            this.emp1 = emp1;
            this.emp2 = emp2;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return emp1 == p.emp1 && emp2 == p.emp2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(emp1, emp2);
        }

        @Override
        public int compareTo(Pair o) {
            if (this.emp1 != o.emp1) {
                return Integer.compare(this.emp1, o.emp1);
            }
            return Integer.compare(this.emp2, o.emp2);
        }
    }
}
