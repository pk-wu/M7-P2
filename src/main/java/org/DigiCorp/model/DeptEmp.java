package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * This class represents the dept_emp table, recording all departments
 * that an employee has worked for before.
 * it has a many-to-one mapping to employee entity and dept_no entity
 */
@Entity
@Table(name="dept_emp")
// uses DeptEmpId class to represent composite primary key
@IdClass(DeptEmpId.class)
public class DeptEmp {

    /**
     * employee number primary composite key, with JsonIgnore preventing serialization
     */
    @Id
    @Column(name="emp_no")
    @JsonIgnore
    private int empNo;

    /**
     * department number primary composite key
     */
    @Id
    @Column(name="dept_no")
    private String deptNo;

    /**
     * fromDate indicates the first date of working in a given department
     */
    @Column(name="from_date")
    private LocalDate fromDate;

    /**
     * toDate indicates the last date of working in a given department.
     * usually has placeholder value '9999-01-01' to indicate current assignment
     */
    @Column(name="to_date")
    private LocalDate toDate;

    // mapping
    /**
     * maps many-to-one relationship to Employee
     * is the foreign key mapping to emp_no
     * JsonIgnore annotations prevents JSON serialization
     */
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * maps many-to-one relationship to Department
     * is the foreign key mapping to dept_no
     * JsonIgnore annotation prevents JSON serialization
     */
    @ManyToOne
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no")
    @JsonIgnore
    private Department department;


    // constructors

    /**
     * default public construct as required by JPA/Hibernate
     */
    public DeptEmp() {}

    /**
     * Parameterized constructor to create DeptEmp instance
     *
     * @param empNo employee unique ID int
     * @param deptNo department unique ID String
     * @param fromDate first date working in department
     * @param toDate last date working in department
     */
    public DeptEmp(int empNo, String deptNo, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // getters and setters

    /**
     * retrieves employee number
     * @return employee number
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * sets the employee number
     * @param empNo the new employee number
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * retrieves the department number
     * @return the department number
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * sets the department number
     * @param deptNo the new department number
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * retrieves the starting date, fromDate
     * @return the start date, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date, fromDate
     * @param fromDate the new starting date, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * retrieves the ending date, toDate
     * @return the ending date, toDate
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * sets the ending date, toDate
     * @param toDate the new ending date, toDate
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * retrieves the employee associated in the mapping
     * @return associated Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets the employee associated in the mapping
     * @param employee the new Employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * retrieves the department associated in the mapping
     * @return associated Department object
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * sets the department associated in the mapping
     * @param department the new Department object
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * provide string representation of DeptEmp
     * @return String formatted DeptEmp with its attributes
     */
    @Override
    public String toString() {
        return "DeptEmp{" +
                "empNo=" + empNo +
                ", deptNo='" + deptNo + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", employee=" + employee +
                ", department=" + department +
                '}';
    }
}
