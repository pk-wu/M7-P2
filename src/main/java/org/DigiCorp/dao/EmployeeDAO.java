package org.DigiCorp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.*;
import org.DigiCorp.exceptions.EmptyResultException;
import org.DigiCorp.exceptions.InvalidDataException;
import org.DigiCorp.model.*;
import org.DigiCorp.util.JPAUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * class provides business logic, middleman between the resource class
 * and the database (manages transactions, executes queries)
 */
public class EmployeeDAO {

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
     * method loads Employee entity and forces initialization, then returns it.
     * If employee does not exist, throws InvalidDataException
     *
     * @param empNo the primary key of the Employee entity
     * @return the Employee retrieved corresponding to the supplied key
     * @throws InvalidDataException if Employee not found
     */
    public Employee getEmployeeRecords(int empNo) throws EmptyResultException {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // find employee via primary key
            Employee emp = em.find(Employee.class, empNo);
            // CHECK: if employee not found, throw EmptyResultException
            if (emp == null) {
                throw new EmptyResultException("Employee (emp no. " + empNo + ") was not found");
            }
            // if employee exists we just return
            return emp;
        }
    }


    /**
     * Service for endpoint #3:
     * executes a named query to retrieve paginated list of EmployeeDTO objects,
     * given a specific department and page.
     *
     * @param deptNo The department number to filter
     * @param page   requested page number for filtering
     * @return paginated List of EmployeeRecordDTO objects, capped at 20 objects
     * @throws InvalidDataException Exception thrown when input data invalid
     * @throws EmptyResultException Exception thrown if page contains no employee
     */
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page) throws InvalidDataException, EmptyResultException {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            // CHECK #1: if department doesn't exist, throw InvalidDataException
            Department dept = em.find(Department.class, deptNo);
            if (dept == null) {
                throw new InvalidDataException("Department " + deptNo + " does not exist.");
            }
            // CHECK #2: if page <1, throw InvalidDataException
            if (page < 1) {
                throw new InvalidDataException("Page number must be greater than or equal to 1!");
            }

            // execute named query to retrieve List of EmployeeDTO records
            // we supply deptNo as a key, convert the page to 0-index, cap the results to 20
            List<EmployeeRecordDTO> results = em.createNamedQuery("Employee.getDepartmentEmployeeRecords", EmployeeRecordDTO.class)
                    .setParameter("deptNo", deptNo)
                    .setFirstResult((page - 1) * 20)
                    .setMaxResults(20)
                    .getResultList();
            // CHECK #3: if empty results, throw EmptyResultException
            if (results.isEmpty()) {
                throw new EmptyResultException("Page index contains no employee records!");
            }
            return results;
        }
    }


    // service for endpoint #4
    public void promoteEmployee(EmployeePromotionRequest request) {
        LocalDate today = LocalDate.now();

        try (EntityManager em = JPAUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Employee emp = em.find(Employee.class, request.getEmpNo());
            if (emp == null) {
                tx.rollback();
                throw new IllegalArgumentException("Employee not found");
            }

            // force initialization of collections to avoid LazyInitializationException
            emp.getSalaryList().size();
            emp.getTitleList().size();
            emp.getDeptEmpList().size();
            emp.getDeptManagerList().size();

            // Get current title and salary
            List<Title> titles = emp.getTitleList();
            Title currentTitle = titles.isEmpty() ? null : titles.get(titles.size() - 1);

            List<Salary> salaries = emp.getSalaryList();
            Salary currentSalary = salaries.isEmpty() ? null : salaries.get(salaries.size() - 1);

            String oldTitle = (currentTitle != null) ? currentTitle.getTitle() : "";
            int oldSalary = (currentSalary != null) ? currentSalary.getSalary() : 0;
            String newTitle = request.getNewTitle();
            int newSalary = request.getNewSalary();

            // --- Validation logic ---
            if (newSalary <= 0) {
                tx.rollback();
                throw new IllegalArgumentException("0 or negative salary not allowed");
            }

            if (oldTitle.equals(newTitle)) {
                if (newSalary < oldSalary) {
                    tx.rollback();
                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
                }
            } else { // title is different
                if (newSalary < oldSalary) {
                    tx.rollback();
                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
                }
            }

            // --- Close current title ---
            if (currentTitle != null) {
                currentTitle.setToDate(today.minusDays(1));
                em.merge(currentTitle);
            }

            // --- Insert new title ---
            Title newTitleEntity = new Title();
            newTitleEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
            newTitleEntity.setTitle(newTitle);
            newTitleEntity.setFromDate(today);
            newTitleEntity.setToDate(LocalDate.of(9999, 12, 31));
            //em.persist(newTitleEntity);
            em.merge(newTitleEntity);

            // --- Close current salary & insert new one ---
            if (newSalary > 0) {
                if (currentSalary != null) {
                    currentSalary.setToDate(today.minusDays(1));
                    em.merge(currentSalary);
                }

                Salary newSalaryEntity = new Salary();
                newSalaryEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
                newSalaryEntity.setSalary(newSalary);
                newSalaryEntity.setFromDate(today);
                newSalaryEntity.setToDate(LocalDate.of(9999, 12, 31));
                em.persist(newSalaryEntity);
            }

            tx.commit();
        }
    }

}
