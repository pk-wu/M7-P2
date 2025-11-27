package org.DigiCorp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private LocalDate fromDate;

    @Column(name="to_date")
    private LocalDate toDate;

    // mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    private Employee employee;

    // constructors
    public Title() {}

    public Title(int empNo, String title, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Title{" +
                "empNo=" + empNo +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", employee=" + employee +
                '}';
    }
}
