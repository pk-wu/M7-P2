package org.DigiCorp.model;

import java.io.Serializable;
import java.util.Objects;

public class DeptManagerId implements Serializable {
    private int empNo;
    private String deptNo;

    public DeptManagerId() {}

    public DeptManagerId(int empNo, String deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
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

    // equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DeptManagerId that)) return false;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }
}
