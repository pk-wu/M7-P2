package org.DigiCorp.model;

import jakarta.persistence.*;

@Entity
@Table(name="titles")
@IdClass(TitleId.class)
public class Title {

    @Id
    @Column(name="emp_no")
    private int empNo;

    @Id
    @Column(name="title")
    private String title;

    @Id
    @Column(name="from_date")
    private String fromDate;

    @Column(name="to_date")
    private String toDate;

    public Title() {}

    public Title(int empNo, String title, String fromDate, String toDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Title{" +
                "empNo=" + empNo +
                ", title='" + title + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
