package com.dimitriosliasis.employees.backend.dto;

/**
 * Summarizes the two employees with the maximum total overlap.
 */
public class TopPair {
    private final int emp1;
    private final int emp2;
    private final long totalDays;

    public TopPair(int emp1, int emp2, long totalDays) {
        this.emp1 = emp1;
        this.emp2 = emp2;
        this.totalDays = totalDays;
    }

    public int getEmp1() {
        return emp1;
    }

    public int getEmp2() {
        return emp2;
    }

    public long getTotalDays() {
        return totalDays;
    }

    @Override
    public String toString() {
        return "TopPair{" +
                "emp1=" + emp1 +
                ", emp2=" + emp2 +
                ", totalDays=" + totalDays +
                '}';
    }
}
