package org.DigiCorp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * class serves as composite key for DeptManager, combining the
 * composite primary keys empNo and deptNo
 * Serializable is implemented as required for IdClass implementation
 */
public class DeptManagerId implements Serializable {
    /**
     * employee number that is a primary composite key
     */
    private int empNo;
    /**
     * department number that is a primary composite key
     */
    private String deptNo;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public DeptManagerId() {
    }

    /**
     * parameterized constructor to create new DeptManagerId instance
     *
     * @param empNo  employee unique ID int
     * @param deptNo department unique ID string
     */
    public DeptManagerId(int empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
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
     * sets employee number
     *
     * @param empNo the new employee number
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * retrieves department number
     *
     * @return the department number
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * sets the department number
     *
     * @param deptNo the new department number
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    // equals and hashcode

    /**
     * compares object for equality
     *
     * @param o object to be compared for equality
     * @return boolean true if objects equal or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // provides logic to check for equality
        if (!(o instanceof DeptManagerId that)) return false;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
    }

    /**
     * returns hash code value for object
     *
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
