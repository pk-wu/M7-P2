package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TitleId implements Serializable {
    private int empNo;
    private String title;
    private LocalDate fromDate;

    public TitleId() {}

    public TitleId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    // getters and setters

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TitleId titleId)) return false;
        return empNo == titleId.empNo && Objects.equals(title, titleId.title) && Objects.equals(fromDate, titleId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}
