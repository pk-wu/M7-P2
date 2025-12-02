package org.DigiCorp.dto;

public class EmployeePromotionRequest {
    private Integer empNo;
    private String newTitle;
    private Integer newSalary;
    private String newDeptNo;

    public EmployeePromotionRequest() {}

    public EmployeePromotionRequest(Integer empNo, String newTitle, Integer newSalary, String newDepartment) {
        this.empNo = empNo;
        this.newTitle = newTitle;
        this.newSalary = newSalary;
        this.newDeptNo = newDepartment;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public Integer getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(Integer newSalary) {
        this.newSalary = newSalary;
    }

    public String getNewDeptNo() {
        return newDeptNo;
    }

    public void setNewDeptNo(String newDeptNo) {
        this.newDeptNo = newDeptNo;
    }
}
