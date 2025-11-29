package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="dept_manager")
@IdClass(DeptManagerId.class)
public class DeptManager {

    @Id
    @Column(name="emp_no")
    @JsonIgnore
    private int empNo;

    @Id
    @Column(name="dept_no")
    private String deptNo;

    @Column(name="from_date")
    private LocalDate fromDate;

    @Column(name="to_date")
    private LocalDate toDate;

    // mapping
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no")
    @JsonIgnore
    private Department department;



    // constructors
    public DeptManager() {}

    public DeptManager(int empNo, String deptNo, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.deptNo = deptNo;
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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "DeptManager{" +
                "empNo=" + empNo +
                ", deptNo='" + deptNo + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", employee=" + employee +
                ", department=" + department +
                '}';
    }
}
