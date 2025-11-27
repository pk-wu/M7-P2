package org.DigiCorp.dto;

import java.time.LocalDate;

public class SalaryDTO {
    private int salary;
    private LocalDate fromDate;
    private LocalDate toDate;

    // getters/setters
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
