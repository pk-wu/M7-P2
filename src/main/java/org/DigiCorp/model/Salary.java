package org.DigiCorp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="salaries")
@IdClass(SalaryId.class)
public class Salary {

    @Id
    @Column(name="emp_no")
    @JsonIgnore
    private int empNo;

    @Id
    @Column(name="from_date")
    private LocalDate fromDate;

    @Column(name="to_date")
    private LocalDate toDate;

    @Column(name="salary")
    private int salary;


    // mapping
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonBackReference
    private Employee employee;

    public Salary() {
    }
    public Salary(int empNo, LocalDate fromDate, LocalDate toDate, int salary) {
        this.empNo = empNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.salary = salary;
    }

    // getters and setters

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getFromDate() {
        return fromDate.toString();
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate.toString();
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "empNo=" + empNo +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", salary=" + salary +
                ", employee=" + employee +
                '}';
    }
}
