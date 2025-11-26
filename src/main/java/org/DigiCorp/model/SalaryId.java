package org.DigiCorp.model;

import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class SalaryId implements Serializable {
    private int empNo;
    private String fromDate;

    public SalaryId() {
    }

    public SalaryId(int empNo, String fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SalaryId salaryId)) return false;
        return empNo == salaryId.empNo && Objects.equals(fromDate, salaryId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
