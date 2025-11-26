package org.DigiCorp.model;


import jakarta.persistence.*;

@Entity
@Table(name="salaries")
@IdClass(SalaryId.class)
public class Salary {

    @Id
    @Column(name="emp_no")
    private int empNo;

    @Id
    @Column(name="from_date")
    private String fromDate;

    @Column(name="to_date")
    private String toDate;

    @Column(name="salary")
    private int salary;

    public Salary() {
    }
    public Salary(int empNo, String fromDate, String toDate, int salary) {
        this.empNo = empNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.salary = salary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "empNo=" + empNo +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", salary=" + salary +
                '}';
    }
}
