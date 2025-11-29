package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * class represents titles table, recording history of job titles of
 * an employee.
 * Maps one-to-many relationship with Employee entity.
 * Uses composite primary key TitleId composed of empNo, title, fromDate
 */
@Entity
@Table(name = "titles")
// specifies class to represent composite primary key
@IdClass(TitleId.class)
public class Title {

    /**
     * employee number, part of composite primary key
     * JsonIgnore prevents serialization
     */
    @Id
    @Column(name = "emp_no")
    @JsonIgnore
    private int empNo;

    /**
     * employee's job title, part of composite primary key
     */
    @Id
    @Column(name = "title")
    private String title;

    /**
     * employee's starting date of this title, part of composite primary key
     */
    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * employee's ending date of this title
     */
    @Column(name = "to_date")
    private LocalDate toDate;

    // mapping
    /**
     * maps many-to-one relationship with Employee entity
     * forms foreign key link via emp_no
     * JsonIgnore prevents serialization
     */
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    // constructors

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public Title() {
    }

    /**
     * parameterized constructor to create Title instance
     *
     * @param empNo    employee unique ID int
     * @param title    job title
     * @param fromDate starting date for the title
     * @param toDate   ending date for the title
     */
    public Title(int empNo, String title, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // getters and setters

    /**
     * retrieves employee number
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
     * retrieves the starting date of the title, fromDate
     *
     * @return the starting date of the title, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date of the title, fromDate
     *
     * @param fromDate the new starting date of the title, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * retrieves the ending date of the title, toDate
     *
     * @return the ending date of the title, toDate
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * sets the ending date of the title, toDate
     *
     * @param toDate the new ending date of the title, toDate
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * retrieves the associated Employee entity
     *
     * @return the associated Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets the associated Employee entity
     *
     * @param employee the new associated Employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * provides string representation of Title
     *
     * @return formatted string containing the title attributes
     */
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
