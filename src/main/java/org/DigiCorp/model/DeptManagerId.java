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
     * employee that is a primary composite key
     */
    private int employee;
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
     * @param employee  employee unique ID int
     * @param deptNo department unique ID string
     */
    public DeptManagerId(int employee, String deptNo) {
        this.employee = employee;
        this.deptNo = deptNo;
    }

    // getters and setters

    /**
     * retrieves employee number
     *
     * @return the employee number
     */
    public int getEmpNo() {
        return employee;
    }

    /**
     * sets employee number
     *
     * @param employee the new employee number
     */
    public void setEmpNo(int employee) {
        this.employee = employee;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DeptManagerId that)) return false;
        return employee == that.employee && Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, deptNo);
    }
}
