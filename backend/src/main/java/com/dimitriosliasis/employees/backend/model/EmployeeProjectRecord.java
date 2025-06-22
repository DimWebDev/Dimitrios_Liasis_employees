package com.dimitriosliasis.employees.backend.model;

import java.time.LocalDate;

/**
 * Represents one row of the input CSV:
 * an employee’s assignment to a project over a date range.
 */
public class EmployeeProjectRecord {
    private final int empId;
    private final int projectId;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public EmployeeProjectRecord(int empId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpId() {
        return empId;
    }

    public int getProjectId() {
        return projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        return "EmployeeProjectRecord{" +
                "empId=" + empId +
                ", projectId=" + projectId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
