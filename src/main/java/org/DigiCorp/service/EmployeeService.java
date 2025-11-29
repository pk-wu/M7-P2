package org.DigiCorp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.*;
import org.DigiCorp.model.*;
import org.DigiCorp.util.JPAUtil;

import java.util.List;

/**
 * class provides business logic, middleman between the resource class
 * and the database (manages transactions, executes queries)
 */
public class EmployeeService {

    /**
     * Service for endpoint #1:
     * executes a named query to retrieve a list of Departments
     *
     * @return List of Department entities
     */
    public List<Department> findAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // retrieves all departments using the named query
            TypedQuery<Department> query = em.createNamedQuery("Department.findAllDepartments", Department.class);
            return query.getResultList();
        }
    }

    /**
     * Service for endpoint #2:
     * method loads Employee entity and forces initialization, then returns it
     *
     * @param empNo The primary key of the Employee entity
     * @return The Employee retrieved corresponding to the supplied primary key
     */
    public Employee getEmployeeRecords(int empNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // find employee via primary key
            Employee emp = em.find(Employee.class, empNo);
            // check if employee found, then force initialization
            if (emp != null) {
                emp.getSalaryList().size();
                emp.getDeptEmpList().size();
                emp.getDeptManagerList().size();
                emp.getTitleList().size();
            }
            return emp;
        }
    }

    /**
     * helper method for endpoint #2:
     * checks whether a supplied String matches a real department number
     *
     * @param deptNo supplied department number to check for existence
     * @return Department entity if it exists or null otherwise
     */
    public Department getDepartment(String deptNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Department dept = em.find(Department.class, deptNo);
            return dept;
        }
    }

    /**
     * Service for endpoint #3:
     * executes a named query to retrieve paginated list of EmployeeDTO objects,
     * given a specific department and page
     *
     * @param deptNo The department number to filter
     * @param page   requested page number for filtering
     * @return paginated List of EmployeeRecordDTO objects, capped at 20 objects
     */
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // execute named query to retreive List of EmployeeDTO records
            // we supply deptNo as a key, convert the page to 0-index, cap the results to 20
            List<EmployeeRecordDTO> results = em.createNamedQuery("Employee.getDepartmentEmployeeRecords", EmployeeRecordDTO.class)
                    .setParameter("deptNo", deptNo)
                    .setFirstResult((page - 1) * 20)
                    .setMaxResults(20)
                    .getResultList();
            return results;
        }
    }
}