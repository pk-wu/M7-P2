package org.DigiCorp.dto;

import java.time.LocalDate;

public class TitleDTO {
    private String title;
    private LocalDate fromDate;
    private LocalDate toDate;

    // getters/setters
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

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}