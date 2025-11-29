package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * class serves as composite primary key for Title entity,
 * composed of employee number, job title, starting date of title.
 * implements Serializable as required for @IdClass implemenation
 */
public class TitleId implements Serializable {
    /**
     * employee number, part of primary composite key
     */
    private int empNo;

    /**
     * job title, part of primary composite key
     */
    private String title;

    /**
     * starting date of title, part of primary composite key
     */
    private LocalDate fromDate;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public TitleId() {
    }

    /**
     * parameterized constructor for creating new TitleId instance
     *
     * @param empNo    employee unique ID int
     * @param title    job title
     * @param fromDate starting date of the job title
     */
    public TitleId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    // getters and setters

    /**
     * retrieve the employee number
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
     * retrieves the job title
     *
     * @return the job title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the job title
     *
     * @param title the new job title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * retrieves the starting date of job title, fromDate
     *
     * @return the starting date of job title, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date of job title, fromDate
     *
     * @param fromDate the new starting date of job title, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * compares object and checks for equality
     *
     * @param o object to be compared for equality
     * @return boolean true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // equality checking logic
        if (!(o instanceof TitleId titleId)) return false;
        return empNo == titleId.empNo && Objects.equals(title, titleId.title) && Objects.equals(fromDate, titleId.fromDate);
    }

    /**
     * returns hash code value for the object
     *
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }
}
