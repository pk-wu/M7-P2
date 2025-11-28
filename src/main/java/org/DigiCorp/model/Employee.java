package org.DigiCorp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@NamedQuery(name="Employee.getDepartmentEmployeeRecords",
        query="SELECT new org.DigiCorp.dto.EmployeeRecordDTO(e.empNo, e.hireDate, e.firstName, e.lastName) " +
                "FROM DeptEmp de JOIN de.employee e " +
                "WHERE de.deptNo = :deptNo ORDER BY e.empNo")
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    private LocalDate hireDate;


    // --- Mapping ---
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Salary> salaryList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Title> titleList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeptEmp> deptEmpList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeptManager> deptManagerList;


    // --- Constructors ---
    public Employee() {
    }

    public Employee(int empNo, LocalDate birthDate, String firstName,
                    String lastName, String gender, LocalDate hireDate) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    // --- Getters and Setters ---
    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getBirthDate() {
        return birthDate.toString();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate.toString();
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public List<Salary> getSalaryList() {
        return salaryList;
    }

    public void setSalaryList(List<Salary> salaryList) {
        this.salaryList = salaryList;
    }

    public List<Title> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    public List<DeptEmp> getDeptEmpList() {
        return deptEmpList;
    }

    public void setDeptEmpList(List<DeptEmp> deptEmpList) {
        this.deptEmpList = deptEmpList;
    }

    public List<DeptManager> getDeptManagerList() {
        return deptManagerList;
    }

    public void setDeptManagerList(List<DeptManager> deptManagerList) {
        this.deptManagerList = deptManagerList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate=" + hireDate +
                ", salaryList=" + salaryList +
                ", titleList=" + titleList +
                ", deptEmpList=" + deptEmpList +
                ", deptManagerList=" + deptManagerList +
                '}';
    }
}
