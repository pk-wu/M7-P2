package org.DigiCorp.dto;

public class EmployeePromotionRequest {
    private int empNo;
    private String newTitle;
    private int newSalary;

    public EmployeePromotionRequest() {}

    public EmployeePromotionRequest(int empNo, String newTitle, int newSalary) {
        this.empNo = empNo;
        this.newTitle = newTitle;
        this.newSalary = newSalary;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public int getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(int newSalary) {
        this.newSalary = newSalary;
    }
}
