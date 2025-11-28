package org.DigiCorp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.*;
import org.DigiCorp.model.*;
import org.DigiCorp.util.JPAUtil;

import java.util.List;

public class EmployeeService {

    // service for endpoint #1
    public List<Department> findAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<Department> query = em.createNamedQuery("Department.findAllDepartments", Department.class);
            return query.getResultList();
        }
    }

    // service for endpoint #2
    public Employee getEmployeeRecords(int empNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Employee emp = em.find(Employee.class, empNo);
            if (emp != null) {
                // force initialization of lazy collections while session is open
                emp.getSalaryList().size();
                emp.getDeptEmpList().size();
                emp.getDeptManagerList().size();
                emp.getTitleList().size();
            }
            return emp;
        }
    }

    // endpoint #2 helper: check department validity
    public Department getDepartment(String deptNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Department dept = em.find(Department.class, deptNo);
            return dept;
        }
    }

    // service for endpoint #3
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            List<EmployeeRecordDTO> results = em.createNamedQuery("Employee.getDepartmentEmployeeRecords", EmployeeRecordDTO.class)
                    .setParameter("deptNo", deptNo)
                    .setFirstResult((page - 1) * 20)
                    .setMaxResults(20)
                    .getResultList();
            return results;
        }
    }
}