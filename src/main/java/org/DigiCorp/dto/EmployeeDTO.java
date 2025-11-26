package org.DigiCorp.dto;

import java.util.List;

public class EmployeeDTO {
    private int empNo;
    private String birthDate;
    private String hireDate;
    private String firstName;
    private String lastName;
    private String gender;
    private List<SalaryDTO> salaries;
    private List<TitleDTO> titles;
    private List<DepartmentDTO> departments;

    // getters/setters
    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<SalaryDTO> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<SalaryDTO> salaries) {
        this.salaries = salaries;
    }

    public List<TitleDTO> getTitles() {
        return titles;
    }

    public void setTitles(List<TitleDTO> titles) {
        this.titles = titles;
    }

    public List<DepartmentDTO> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentDTO> departments) {
        this.departments = departments;
    }
}




