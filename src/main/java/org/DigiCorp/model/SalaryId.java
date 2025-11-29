package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * class serves as composite primary key for Salary entity
 * combines employee number and start date to uniquely
 * identify Salary record.
 * implements Serializable as required for @IdClass implementation
 */
public class SalaryId implements Serializable {
    /**
     * employee's id number, part of primary composite key
     */
    private int empNo;
    /**
     * employee's start date of salary, part of primary composite key
     */
    private LocalDate fromDate;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public SalaryId() {
    }

    /**
     * parameterized constructor to create new composite key instance
     *
     * @param empNo    employee's unique ID int
     * @param fromDate starting date of the salary entry
     */
    public SalaryId(int empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    // getters and setters

    /**
     * retrieves the employee number
     *
     * @return the employee number
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * sets the employee number
     *
     * @param empNo the new employee number
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * retrieves the starting date, fromDate
     *
     * @return the starting date, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date, fromDate
     *
     * @param fromDate the new starting date, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // equals and hashcode

    /**
     * compares object for equality
     *
     * @param o object to be compared for equality
     * @return boolean true if equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SalaryId salaryId)) return false;
        return empNo == salaryId.empNo && Objects.equals(fromDate, salaryId.fromDate);
    }

    /**
     * returns hash code value for the object
     *
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
