package org.DigiCorp.dto;


import java.time.LocalDate;

public class EmployeeRecordDTO {
    private int empNo;
    private LocalDate hireDate;
    private String firstName;
    private String lastName;

    // constructors


    public EmployeeRecordDTO() {
    }

    public EmployeeRecordDTO(int empNo, LocalDate hireDate, String firstName, String lastName) {
        this.empNo = empNo;
        this.hireDate = hireDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters/setters
    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
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
}




