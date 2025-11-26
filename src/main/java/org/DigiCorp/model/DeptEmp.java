package org.DigiCorp.model;

import jakarta.persistence.*;

@Entity
@Table(name="dept_emp")
@IdClass(DeptEmpId.class)
public class DeptEmp {

    @Id
    @Column(name="emp_no")
    private int empNo;

    @Id
    @Column(name="dept_no")
    private String deptNo;

    @Column(name="from_date")
    private String fromDate;

    @Column(name="to_date")
    private String toDate;

    public DeptEmp() {}

    public DeptEmp(int empNo, String deptNo, String fromDate, String toDate) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "DeptEmp{" +
                "empNo=" + empNo +
                ", deptNo='" + deptNo + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
