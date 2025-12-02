package org.DigiCorp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.*;
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

    // list of allowed titles
    private final List<String> ALLOWED_TITLES = List.of(
            "Assistant Engineer",
            "Engineer",
            "Senior Engineer",
            "Staff",
            "Senior Staff",
            "Technique Leader",
            "Manager"
    );

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
     *
     * @param empNo the primary key of the Employee entity
     * @return the Employee retrieved corresponding to the supplied key
     */
    public Employee getEmployeeRecords(int empNo) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // find employee via primary key
            Employee emp = em.find(Employee.class, empNo);
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
     */
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page) throws InvalidDataException {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            // CHECK: if department doesn't exist, throw InvalidDataException
            Department dept = em.find(Department.class, deptNo);
            if (dept == null) {
                throw new InvalidDataException("Department " + deptNo + " does not exist.");
            }

            // execute named query to retrieve List of EmployeeDTO records
            // we supply deptNo as a key, convert the page to 0-index, cap the results to 20
            List<EmployeeRecordDTO> results = em.createNamedQuery("Employee.getDepartmentEmployeeRecords", EmployeeRecordDTO.class)
                    .setParameter("deptNo", deptNo)
                    .setFirstResult((page - 1) * 20)
                    .setMaxResults(20)
                    .getResultList();
            return results;
        }
    }


    // service for endpoint #4
//    public void promoteEmployee(EmployeePromotionRequest request) {
//        LocalDate today = LocalDate.now();
//
//        try (EntityManager em = JPAUtil.getEntityManager()) {
//            EntityTransaction tx = em.getTransaction();
//            tx.begin();
//
//            Employee emp = em.find(Employee.class, request.getEmpNo());
//            if (emp == null) {
//                tx.rollback();
//                throw new IllegalArgumentException("Employee not found");
//            }
//
//            // force initialization of collections to avoid LazyInitializationException
//            emp.getSalaryList().size();
//            emp.getTitleList().size();
//            emp.getDeptEmpList().size();
//            emp.getDeptManagerList().size();
//
//            // Get current title and salary
//            List<Title> titles = emp.getTitleList();
//            Title currentTitle = titles.isEmpty() ? null : titles.get(titles.size() - 1);
//
//            List<Salary> salaries = emp.getSalaryList();
//            Salary currentSalary = salaries.isEmpty() ? null : salaries.get(salaries.size() - 1);
//
//            String oldTitle = (currentTitle != null) ? currentTitle.getTitle() : "";
//            int oldSalary = (currentSalary != null) ? currentSalary.getSalary() : 0;
//            String newTitle = request.getNewTitle();
//            int newSalary = request.getNewSalary();
//
//            // --- Validation logic ---
//            if (newSalary <= 0) {
//                tx.rollback();
//                throw new IllegalArgumentException("0 or negative salary not allowed");
//            }
//
//            if (oldTitle.equals(newTitle)) {
//                if (newSalary < oldSalary) {
//                    tx.rollback();
//                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
//                }
//            } else { // title is different
//                if (newSalary < oldSalary) {
//                    tx.rollback();
//                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
//                }
//            }
//
//            // --- Close current title ---
//            if (currentTitle != null) {
//                currentTitle.setToDate(today.minusDays(1));
//                em.merge(currentTitle);
//            }
//
//            // --- Insert new title ---
//            Title newTitleEntity = new Title();
//            newTitleEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
//            newTitleEntity.setTitle(newTitle);
//            newTitleEntity.setFromDate(today);
//            newTitleEntity.setToDate(LocalDate.of(9999, 12, 31));
//            //em.persist(newTitleEntity);
//            em.merge(newTitleEntity);
//
//            // --- Close current salary & insert new one ---
//            if (newSalary > 0) {
//                if (currentSalary != null) {
//                    currentSalary.setToDate(today.minusDays(1));
//                    em.merge(currentSalary);
//                }
//
//                Salary newSalaryEntity = new Salary();
//                newSalaryEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
//                newSalaryEntity.setSalary(newSalary);
//                newSalaryEntity.setFromDate(today);
//                newSalaryEntity.setToDate(LocalDate.of(9999, 12, 31));
//                em.persist(newSalaryEntity);
//            }
//
//            tx.commit();
//        }
//    }

    public void promoteEmployee(EmployeePromotionRequest request) throws InvalidDataException {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // CHECK: Employee must exist
            Employee emp = em.find(Employee.class, request.getEmpNo());
            if (emp == null) {
                throw new InvalidDataException("Employee does not exist");
            }

            // Get current title, salary, deptEmp  , and deptManager
            List<Title> titles = emp.getTitleList();
            Title currentTitle = titles.isEmpty() ? null : titles.get(titles.size() - 1);

            List<Salary> salaries = emp.getSalaryList();
            Salary currentSalary = salaries.isEmpty() ? null : salaries.get(salaries.size() - 1);

            List<DeptEmp> deptEmps = emp.getDeptEmpList();
            DeptEmp currentDeptEmp = deptEmps.isEmpty() ? null : deptEmps.get(deptEmps.size() - 1);

            List<DeptManager> deptManagers = emp.getDeptManagerList();
            DeptManager currentDeptManager = deptManagers.isEmpty() ? null : deptManagers.get(deptManagers.size() - 1);


            // CHECK: if Employee must be current employee
            if (currentSalary.getToDate().compareTo(LocalDate.of(9999, 01, 01)) != 0) {
                throw new InvalidDataException("Employee is no longer with the company");
            }
            // CHECK: at least one value is being updated
            if (request.getNewSalary() == null && request.getNewTitle() == null && request.getNewDeptNo() == null) {
                throw new InvalidDataException("No data was supplied");
            }
            // CHECK: is there any real update
            boolean salaryChanged = request.getNewSalary() != null
                    && request.getNewSalary() != currentSalary.getSalary();

            boolean deptChanged = request.getNewDeptNo() != null
                    && !request.getNewDeptNo().equals(currentDeptEmp.getDeptNo());

            boolean titleChanged = request.getNewTitle() != null
                    && !request.getNewTitle().equalsIgnoreCase(currentTitle.getTitle());

            if (!salaryChanged && !deptChanged && !titleChanged) {
                throw new InvalidDataException("Data supplied matches existing data");
            }

            // wrap up remaining logic in transaction, so if exceptions are thrown we can roll it back
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                LocalDate today = LocalDate.now();

                // --- update salary ---
                // attempt salary update if supplied in the payload
                if (salaryChanged) {
                    // CHECK: salary value is legal
                    if (request.getNewSalary() < 1 || request.getNewSalary() > 999999999) {
                        throw new InvalidDataException("Salary value out of range");
                    }
                    // CHECK: if ONLY salary is being updated, salary must go up
                    if (!titleChanged && !deptChanged) {
                        if (currentSalary.getSalary() >= request.getNewSalary()) {
                            throw new InvalidDataException("New salary must be greater than previous salary");
                        }
                    }
                    // CHECK: disallow updating if the composite key we try to use already exists
                    for (Salary salary : salaries) {
                        if (salary.getFromDate().isEqual(today)) {
                            throw new InvalidDataException("Salary value has already been updated today");
                        }
                    }
                    // Perform salary update:
                    // update old salary entry, insert the new salary entry
                    currentSalary.setToDate(today);
                    Salary newSalary = new Salary(emp, today, LocalDate.of(9999, 01, 01), request.getNewSalary());
                    em.merge(currentSalary);
                    em.persist(newSalary);
                }

                // -- update departmentNo ---
                if (deptChanged) {
                    // CHECK: departmentNo supplied exists
                    if (em.find(Department.class, request.getNewDeptNo()) == null) {
                        throw new InvalidDataException("Department " + request.getNewDeptNo() + " does not exist.");
                    }

                    // CHECK: if ONLY department is being updated, departmentNo must change
                    if (!titleChanged && !salaryChanged) {
                        if (currentDeptEmp.getDeptNo().equals(request.getNewDeptNo())) {
                            throw new InvalidDataException("Employee already in department " + request.getNewDeptNo());
                        }
                    }

                    // CHECK: disallow updating if the composite key we try to use already exists
                    // salary change + dept same should not enter new entry
//                    for (DeptEmp deptEmp : deptEmps) {
//                        if (deptEmp.getDeptNo().equals(request.getNewDeptNo())) {
//                            throw new InvalidDataException("Employee cannot return to their previous department");
//                        }
//                    }
                    for (DeptEmp deptEmp : deptEmps) {
                        if (deptEmp.getDeptNo().equals(request.getNewDeptNo())) {
                            throw new InvalidDataException("Employee cannot return to their previous department");
                        }
                    }
                    // Perform deptEmp update:
                    // update old deptEmp entry, insert the new deptEmp entry
                    currentDeptEmp.setToDate(today);
                    DeptEmp newDeptEmp = new DeptEmp(emp, request.getNewDeptNo(), today, LocalDate.of(9999, 01, 01));
                    em.merge(currentDeptEmp);
                    em.persist(newDeptEmp);

                }

                // --- update title ---
                if (titleChanged) {
                    // CHECK: new title supplied is legal
//                    if (!ALLOWED_TITLES.contains(request.getNewTitle())) {
//                        throw new InvalidDataException("Title " + request.getNewTitle() + " does not exist");
//                    }
                    // CHECK: if ONLY title is being updated, title must change
                    if (!salaryChanged && !deptChanged) {
                        if (currentTitle.getTitle().equalsIgnoreCase(request.getNewTitle())) {
                            throw new InvalidDataException("Employee already has title " + request.getNewTitle());
                        }
                    }

                    // CHECK: disallow updating if the composite key we try to use already exists
                    for (Title title : titles) {
                        if (title.getTitle().equalsIgnoreCase(request.getNewTitle()) && title.getFromDate().isEqual(today)) {
                            throw new InvalidDataException("Employee has already been promoted to this title today");
                        }
                    }
                    // Perform title update:
                    // update old title entry, insert the new title entry

                    // ensure title input is title case
                    StringBuilder titleCasedTitle = new StringBuilder();
                    for (String word : request.getNewTitle().split("\\s+")) {
                        if (!word.isEmpty()) {
                            titleCasedTitle.append(Character.toUpperCase(word.charAt(0)))
                                    .append(word.substring(1).toLowerCase())
                                    .append(" ");
                        }
                    }

                    currentTitle.setToDate(today);
                    Title newTitle = new Title(emp, titleCasedTitle.toString().trim(), today, LocalDate.of(9999, 01, 01));
                    em.merge(currentTitle);
                    em.persist(newTitle);

                    // if title is manager, requires additional handling
                    // Manager -> Non-Manager
                    if (currentTitle.getTitle().equals("Manager") && !request.getNewTitle().equals("Manager")) {
                        // set end date for manager
                        currentDeptManager.setToDate(today);
                        em.merge(currentDeptManager);
                    }
                    // xx -> Manager
                    if (request.getNewTitle().equals("Manager")) {
                        // we need targetDept in the event we retain same dept & promote
                        String targetDept = (request.getNewDeptNo() != null)
                                ? request.getNewDeptNo()
                                : currentDeptEmp.getDeptNo();
                        // CHECK: disallow updating if the composite key we try to use already exists
                        boolean duplicateManager = false;
                        for (DeptManager deptMgr : deptManagers) {
                            if (deptMgr.getDeptNo().equals(targetDept)) {
                                duplicateManager = true;
                                break;
                            }
                        }
                        // Non-Manager -> Manager
                        if (!currentTitle.getTitle().equals("Manager")) {
                            // add new entry into manager table, with the same department
                            if (duplicateManager) {
                                throw new InvalidDataException("Employee was previously a manager in this department!");
                            }
                            DeptManager newManager = new DeptManager(emp, targetDept, today, LocalDate.of(9999, 01, 01));
                            em.persist(newManager);
                        }
                        // Manager -> Manager (salary/dept change)
                        if (currentTitle.getTitle().equals("Manager")) {
                            if (request.getNewDeptNo() != null && !currentDeptEmp.getDeptNo().equals(request.getNewDeptNo())) {
                                if (duplicateManager) {
                                    throw new InvalidDataException("Employee was previously a manager in this department!");
                                }
                                // updated department but still manager: close manager entry, create new manager entry
                                currentDeptManager.setToDate(today);
                                em.merge(currentDeptManager);
                                // add new entry into manager table, with new department
                                DeptManager newManager = new DeptManager(emp, targetDept, today, LocalDate.of(9999, 01, 01));
                                em.persist(newManager);
                            }
                        }
                    }
                }

                // if reach the end we can commit the changes we have done
                tx.commit();
            } catch (InvalidDataException e) {
                tx.rollback();
                throw new InvalidDataException(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

//        LocalDate today = LocalDate.now();
//
//        try (EntityManager em = JPAUtil.getEntityManager()) {
//            EntityTransaction tx = em.getTransaction();
//            tx.begin();
//
//            Employee emp = em.find(Employee.class, request.getEmpNo());
//            if (emp == null) {
//                tx.rollback();
//                throw new IllegalArgumentException("Employee not found");
//            }
//
//            // force initialization of collections to avoid LazyInitializationException
//            emp.getSalaryList().size();
//            emp.getTitleList().size();
//            emp.getDeptEmpList().size();
//            emp.getDeptManagerList().size();
//
//            // Get current title and salary
//            List<Title> titles = emp.getTitleList();
//            Title currentTitle = titles.isEmpty() ? null : titles.get(titles.size() - 1);
//
//            List<Salary> salaries = emp.getSalaryList();
//            Salary currentSalary = salaries.isEmpty() ? null : salaries.get(salaries.size() - 1);
//
//            String oldTitle = (currentTitle != null) ? currentTitle.getTitle() : "";
//            int oldSalary = (currentSalary != null) ? currentSalary.getSalary() : 0;
//            String newTitle = request.getNewTitle();
//            int newSalary = request.getNewSalary();
//
//            // --- Validation logic ---
//            if (newSalary <= 0) {
//                tx.rollback();
//                throw new IllegalArgumentException("0 or negative salary not allowed");
//            }
//
//            if (oldTitle.equals(newTitle)) {
//                if (newSalary < oldSalary) {
//                    tx.rollback();
//                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
//                }
//            } else { // title is different
//                if (newSalary < oldSalary) {
//                    tx.rollback();
//                    throw new IllegalArgumentException("Salary Decrement Not Allowed");
//                }
//            }
//
//            // --- Close current title ---
//            if (currentTitle != null) {
//                currentTitle.setToDate(today.minusDays(1));
//                em.merge(currentTitle);
//            }
//
//            // --- Insert new title ---
//            Title newTitleEntity = new Title();
//            newTitleEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
//            newTitleEntity.setTitle(newTitle);
//            newTitleEntity.setFromDate(today);
//            newTitleEntity.setToDate(LocalDate.of(9999, 12, 31));
//            //em.persist(newTitleEntity);
//            em.merge(newTitleEntity);
//
//            // --- Close current salary & insert new one ---
//            if (newSalary > 0) {
//                if (currentSalary != null) {
//                    currentSalary.setToDate(today.minusDays(1));
//                    em.merge(currentSalary);
//                }
//
//                Salary newSalaryEntity = new Salary();
//                newSalaryEntity.setEmployee(emp); // Employee object, matches @IdClass mapping
//                newSalaryEntity.setSalary(newSalary);
//                newSalaryEntity.setFromDate(today);
//                newSalaryEntity.setToDate(LocalDate.of(9999, 12, 31));
//                em.persist(newSalaryEntity);
//            }
//
//            tx.commit();
//        }
//    }

}
