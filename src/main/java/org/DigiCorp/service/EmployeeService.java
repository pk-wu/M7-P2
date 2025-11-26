package org.DigiCorp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.DepartmentDTO;
import org.DigiCorp.dto.EmployeeDTO;
import org.DigiCorp.dto.SalaryDTO;
import org.DigiCorp.dto.TitleDTO;
import org.DigiCorp.model.*;
import org.DigiCorp.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    // service for endpoint #1
    public List<Department> findAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT d FROM Department d", Department.class)
                    .getResultList();
        }
    }

    // service for endpoint #2
    public EmployeeDTO getEmployeeRecords(int empNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Employee emp = em.find(Employee.class, empNo);
            if (emp == null) return null;

            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmpNo(emp.getEmpNo());
            dto.setBirthDate(emp.getBirthDate());
            dto.setHireDate(emp.getHireDate());
            dto.setFirstName(emp.getFirstName());
            dto.setLastName(emp.getLastName());
            dto.setGender(emp.getGender());

            // Salaries
            List<Salary> salaries = em.createQuery(
                            "SELECT s FROM Salary s WHERE s.empNo = :empNo", Salary.class)
                    .setParameter("empNo", empNo)
                    .getResultList();
            List<SalaryDTO> salaryDTOs = new ArrayList<>();
            for (Salary s : salaries) {
                SalaryDTO sdto = new SalaryDTO();
                sdto.setSalary(s.getSalary());
                sdto.setFromDate(s.getFromDate());
                sdto.setToDate(s.getToDate());
                salaryDTOs.add(sdto);
            }
            dto.setSalaries(salaryDTOs);

            // Titles
            List<Title> titles = em.createQuery(
                            "SELECT t FROM Title t WHERE t.empNo = :empNo", Title.class)
                    .setParameter("empNo", empNo)
                    .getResultList();
            List<TitleDTO> titleDTOs = new ArrayList<>();
            for (Title t : titles) {
                TitleDTO tdto = new TitleDTO();
                tdto.setTitle(t.getTitle());
                tdto.setFromDate(t.getFromDate());
                tdto.setToDate(t.getToDate());
                titleDTOs.add(tdto);
            }
            dto.setTitles(titleDTOs);

            // Departments
            List<DeptEmp> deptEmpList = em.createQuery(
                            "SELECT de FROM DeptEmp de WHERE de.empNo = :empNo", DeptEmp.class)
                    .setParameter("empNo", empNo)
                    .getResultList();
            List<DepartmentDTO> deptDTOs = new ArrayList<>();
            for (DeptEmp de : deptEmpList) {
                DepartmentDTO ddto = new DepartmentDTO();
                ddto.setDeptNo(de.getDeptNo());
                Department dept = em.find(Department.class, de.getDeptNo());
                ddto.setDeptName(dept != null ? dept.getDeptName() : null);
                ddto.setFromDate(de.getFromDate());
                ddto.setToDate(de.getToDate());
                deptDTOs.add(ddto);
            }
            dto.setDepartments(deptDTOs);

            return dto;
        }
    }

}
