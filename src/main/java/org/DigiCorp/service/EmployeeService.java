package org.DigiCorp.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.dto.EmployeeRecordDTO;
import org.DigiCorp.exceptions.InvalidDataException;
import org.DigiCorp.helper.Helper;
import org.DigiCorp.model.Department;
import org.DigiCorp.model.Employee;
import org.DigiCorp.dao.EmployeeDAO;

import java.util.List;

/**
 * EmployeeResource class provides the REST endpoints as per /api/employees/
 */
@Path("/employees")
public class EmployeeService {

    /**
     * enables us to access business logic in EmployeeService
     */
    private final EmployeeDAO employeeDAO;

    /**
     * default constructor, initializes the employee service object for use
     */
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    /**
     * Endpoint #1: Get all departments
     * Retrieves list of all Department records
     * Usage (GET): http://localhost:8090/M7_P2_war_exploded/api/employees/getAllDepartments
     *
     * @return response containing JSON of list of Department objects
     */
    @GET
    @Path("/getAllDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {
        // call service to retrieve a list of all departments
        List<Department> list = employeeDAO.findAllDepartments();
        return Response.ok().entity(list).build();
    }

    /**
     * Endpoint #2: Get specific Employee record
     * Retrieves full employee record and related details i.e. salaries, titles, departments.
     * If employee cannot be found, error message is printed
     * Usage (GET): http://localhost:8090/M7_P2_war_exploded/api/employees/getEmployeeRecord/?empNo=99999
     *
     * @param empNo Employee number to be retrieved, taken in as a Query Parameter
     * @return JSON list of Employee Records or failure message if employee does not exist
     */
    @GET
    @Path("/getEmployeeRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRecord(@QueryParam("empNo") int empNo) {
        // Retrieve employee record
        Employee emp = employeeDAO.getEmployeeRecords(empNo);
        if (emp == null) {
            // if employee not found, catch exception and give Response
            return Response.ok()
                    .entity("Requested Employee Record not found")
                    .build();
        }

        // return the requested Employee if all ok
        return Response.ok().entity(emp).build();

    }

    /**
     * Endpoint #3: Get paginated EmployeeDTO records by department
     * Retrieves paginated list of EmployeeDTO records belonging to some department,
     * where the requested department is supplied as an argument and each page is
     * limited to 20 entries.
     * If department does not exist, page number invalid, or current page index has no employees,
     * exceptions are caught and handled.
     *
     * Usages (GET): (1) defaulted to page 1 (2) specifying page number
     * (1) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003
     * (2) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003&page=10
     *
     * @param departmentNo name of the department we wish to retrieve employees from
     * @param page         1-indexed page number of the list we want. optional and defaults to 1
     * @return JSON list of EmployeeRecordDTO if success or some HTTP errors upon validation failure
     */
    @GET
    @Path("/getAllEmployeeRecords")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployeeRecords(
            @QueryParam("departmentNo") String departmentNo,
            @QueryParam("page") @DefaultValue("1") int page) {
        // CHECK: page number has to be greater than or equal to 1
        if (page < 1) {
            return Response.status(400).entity("Page number must be greater than or equal to 1!").build();
        }
        try {
            // retrieve the list of employee records
            List<EmployeeRecordDTO> empRecords = employeeDAO.getAllEmployeeRecordsList(departmentNo, page);
            // CHECK: if retrieved page is empty we return appropriate message
            if (empRecords.isEmpty()) {
                return Response.ok()
                        .entity("Page index contains no employee records!").build();
            }
            return Response.ok().entity(empRecords).build();
        } catch (InvalidDataException e) {
            return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
        }
    }

    // endpoint #4
    @POST
    @Path("/promote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(EmployeePromotionRequest request) {
        // try to process promotion request, throw & catch errors if unsuccessful
        try {
            // call helper method to validate, throws exception if invalid
            Helper.validateRequest(request);

            // call your service to promote the employee
            employeeDAO.promoteEmployee(request);

            // return success response
            return Response.status(Response.Status.CREATED)
                    .entity("Employee promoted successfully")
                    .build();
        } catch (InvalidDataException e) {
            // catch exceptions we throw if promotion fails, return a response
            return Response.status(e.getStatusCode())
                    .entity("Promotion failed: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            // troubleshoot more unexpected errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error: " + e.getMessage())
                    .build();
        }
    }
}
