package org.DigiCorp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.model.Department;
import org.DigiCorp.model.Employee;

import java.util.List;

public class EmployeeService {
    protected EntityManager em;

    public EmployeeService(EntityManager em) {
        this.em = em;
    }

    // service for endpoint #1
    public List<Department> findAllDepartments() {
        TypedQuery<Department> query = em.createQuery(
                "SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }

    // service for endpoint #2
    public Employee findEmployee(int empNo) {
        return em.find(Employee.class, empNo);
    }

}
