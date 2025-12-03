package org.DigiCorp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.DigiCorp.dto.*;
import org.DigiCorp.exceptions.InvalidDataException;
import org.DigiCorp.helper.Helper;
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
     * Logic for endpoint #1:
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
     * Logic for endpoint #2:
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
     * Logic for endpoint #3:
     * executes a named query to retrieve paginated list of EmployeeDTO objects,
     * given a specific department and page.
     *
     * @param deptNo The department number to filter
     * @param page   requested page number for filtering
     * @return paginated List of EmployeeRecordDTO objects, capped at 20 objects
     * @throws InvalidDataException throws this if any data validation fails
     */
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page) throws InvalidDataException {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            // CHECK: if dept supplied, does it belong in the department list?
            if (em.find(Department.class, deptNo) == null) {
                throw new InvalidDataException("Department " + deptNo + " does not exist.", 404);
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

    /**
     * Logic for endpoint #4:
     * promotes employee
     *
     * @param request JSON request passed in via postman
     * @throws InvalidDataException thrown when failed to promote for any reason
     */
    public void promoteEmployee(EmployeePromotionRequest request) throws InvalidDataException {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            // CHECK: Employee must exist
            Employee emp = em.find(Employee.class, request.getEmpNo());
            if (emp == null) {
                throw new InvalidDataException("Employee does not exist", 404);
            }

            // Get current title, salary, deptEmp, and deptManager
            //TODO: optimize this assignment (null check done in service)
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
                throw new InvalidDataException("Employee is no longer with the company", 400);
            }
            //TODO: optimize this assignment (null check done in service)
            // CHECK: is there any real update
            boolean salaryChanged = request.getNewSalary() != null
                    && request.getNewSalary() != currentSalary.getSalary();

            boolean deptChanged = request.getNewDeptNo() != null
                    && !request.getNewDeptNo().toLowerCase().equals(currentDeptEmp.getDeptNo());

            boolean titleChanged = request.getNewTitle() != null
                    && !request.getNewTitle().equalsIgnoreCase(currentTitle.getTitle());

            // CHECK: if supplied data same as existing data, no changes made, throw error
            if (!salaryChanged && !deptChanged && !titleChanged) {
                throw new InvalidDataException("Provided data matches existing data, no changes requested", 400);
            }

            // CHECK: if dept supplied, does it belong in the department list?
            if (request.getNewDeptNo() != null) {
                if (em.find(Department.class, request.getNewDeptNo()) == null) {
                    throw new InvalidDataException("Department " + request.getNewDeptNo() + " does not exist.", 404);
                }
            }

            // wrap up remaining logic in transaction, so if exceptions are thrown we can roll it back
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                LocalDate today = LocalDate.now();

                // --- update salary ---
                // attempt salary update if supplied in the payload
                if (salaryChanged) {

                    // CHECK: if ONLY salary is being updated, salary must go up
//                    if (!titleChanged && !deptChanged) {
//                        if (currentSalary.getSalary() >= request.getNewSalary()) {
//                            throw new InvalidDataException("New salary must be greater than previous salary", 400);
//                        }
//                    }
                    // CHECK: disallow updating if the composite key we try to use already exists
                    for (Salary salary : salaries) {
                        if (salary.getFromDate().isEqual(today)) {
                            throw new InvalidDataException("Salary value has already been updated today", 400);
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
                    // CHECK: if ONLY department is being updated, departmentNo must change
                    if (!titleChanged && !salaryChanged) {
                        if (currentDeptEmp.getDeptNo().equalsIgnoreCase(request.getNewDeptNo().toLowerCase())) {
                            throw new InvalidDataException("Employee already in department " + request.getNewDeptNo().toLowerCase(), 400);
                        }
                    }

                    // CHECK: disallow updating if the composite key we try to use already exists
                    for (DeptEmp deptEmp : deptEmps) {
                        if (deptEmp.getDeptNo().equalsIgnoreCase(request.getNewDeptNo().toLowerCase())) {
                            throw new InvalidDataException("Employee cannot return to their previous department", 400);
                        }
                    }
                    // Perform deptEmp update:
                    // update old deptEmp entry, insert the new deptEmp entry
                    currentDeptEmp.setToDate(today);
                    DeptEmp newDeptEmp = new DeptEmp(emp, request.getNewDeptNo().toLowerCase(), today, LocalDate.of(9999, 01, 01));
                    em.merge(currentDeptEmp);
                    em.persist(newDeptEmp);

                }

                // Manager -> Manager
                // we handle here because the title does not change
                if (currentTitle.getTitle().equals("Manager") && !titleChanged && deptChanged) {
                    // get the new department the manager is transferring to
                    String targetDept = request.getNewDeptNo().toLowerCase();

                    // check if the department we want to transfer to already has entry
                    boolean duplicateManager = false;
                    for (DeptManager deptMgr : deptManagers) {
                        if (deptMgr.getDeptNo().equals(targetDept)) {
                            duplicateManager = true;
                            break;
                        }
                    }
                    // throw error if target department already has entry
                    if (duplicateManager) {
                        throw new InvalidDataException("Employee was previously a manager in this department!", 400);
                    }

                    // close current manager record
                    currentDeptManager.setToDate(today);
                    em.merge(currentDeptManager);

                    // open new manager record in new department
                    DeptManager newManager = new DeptManager(emp, targetDept, today, LocalDate.of(9999, 1, 1));
                    em.persist(newManager);
                }


                // --- update title ---
                if (titleChanged) {
                    // use helper method to ensure title input is title case
                    String inputTitle = Helper.toTitleCase(request.getNewTitle());

                    // CHECK: if ONLY title is being updated, title must change
                    if (!salaryChanged && !deptChanged) {
                        if (currentTitle.getTitle().equals(inputTitle)) {
                            throw new InvalidDataException("Employee already has title " + inputTitle, 400);
                        }
                    }

                    // CHECK: disallow updating if the composite key we try to use already exists
                    for (Title title : titles) {
                        if (title.getTitle().equals(inputTitle) && title.getFromDate().isEqual(today)) {
                            throw new InvalidDataException("Employee has already been promoted to this title today", 400);
                        }
                    }
                    // Perform title update:
                    // update old title entry, insert the new title entry
                    currentTitle.setToDate(today);
                    Title newTitle = new Title(emp, inputTitle, today, LocalDate.of(9999, 01, 01));
                    em.merge(currentTitle);
                    em.persist(newTitle);

                    // if title is manager, requires additional handling
                    // Manager -> Non-Manager
                    if (currentTitle.getTitle().equals("Manager") && !inputTitle.equals("Manager")) {
                        // set end date for manager
                        currentDeptManager.setToDate(today);
                        em.merge(currentDeptManager);
                    }
                    // Non-Manager -> Manager
                    if (inputTitle.equals("Manager")) {
                        // we need targetDept handles situation where new dept not supplied
                        String targetDept = (request.getNewDeptNo() != null)
                                ? request.getNewDeptNo().toLowerCase()
                                : currentDeptEmp.getDeptNo();
                        // CHECK: disallow updating if the composite key we try to use already exists
                        boolean duplicateManager = false;
                        for (DeptManager deptMgr : deptManagers) {
                            if (deptMgr.getDeptNo().equals(targetDept)) {
                                duplicateManager = true;
                                break;
                            }
                        }
                        if (duplicateManager) {
                            throw new InvalidDataException("Employee was previously a manager in this department!", 400);
                        }
                        // add new manager record
                        DeptManager newManager = new DeptManager(emp, targetDept.toLowerCase(), today, LocalDate.of(9999, 01, 01));
                        em.persist(newManager);
                    }
                }
                // if reach the end we can commit the changes we have done
                tx.commit();
            } catch (InvalidDataException e) {
                tx.rollback();
                throw new InvalidDataException(e.getMessage(), e.getStatusCode());
            } catch (Exception e) {
                tx.rollback();
                throw new RuntimeException(e);
            }
        }
    }
}
