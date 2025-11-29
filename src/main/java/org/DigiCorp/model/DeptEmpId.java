package org.DigiCorp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite Primary Key for DeptEmp entity, combining the
 * employee number and department number
 *
 * Serializable is implemented as required for @IdClass usage
 */
public class DeptEmpId implements Serializable {
    /**
     * employee number that forms part of the primary composite key
     */
    private int empNo;
    /**
     * department number that forms part of the primary composite key
     */
    private String deptNo;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public DeptEmpId() {}

    /**
     * parameterized constructor for explicit key creation
     * @param empNo the employee's id number
     * @param deptNo the department's id string
     */
    public DeptEmpId(int empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    // getters and setters

    /**
     * retrieves the employee number
     * @return the employee number
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

    // equals and hashcode

    /**
     * Compares the object for equality
     * @param o object to be compared for equality
     * @return boolean result of equals check
     */
    @Override
    public boolean equals(Object o) {
        // object checking implementation logic
        if (!(o instanceof DeptEmpId deptEmpId)) return false;
        return empNo == deptEmpId.empNo && Objects.equals(deptNo, deptEmpId.deptNo);
    }

    /**
     * returns hash code value for object
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
