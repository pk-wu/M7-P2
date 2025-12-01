package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * class serves as composite primary key for Title entity,
 * composed of Employee object, job title, starting date of title.
 * implements Serializable as required for @IdClass implementation
 */
public class TitleId implements Serializable {

    /**
     * Employee object, part of composite primary key
     */
    private Employee employee;

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
     * @param employee Employee object
     * @param title    job title
     * @param fromDate starting date of the job title
     */
    public TitleId(Employee employee, String title, LocalDate fromDate) {
        this.employee = employee;
        this.title = title;
        this.fromDate = fromDate;
    }

    // getters and setters

    /**
     * retrieve the Employee object
     *
     * @return the Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets the Employee object
     *
     * @param employee the new Employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        if (!(o instanceof TitleId titleId)) return false;
        return Objects.equals(employee, titleId.employee)
                && Objects.equals(title, titleId.title)
                && Objects.equals(fromDate, titleId.fromDate);
    }

    /**
     * returns hash code value for the object
     *
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(employee, title, fromDate);
    }
}
