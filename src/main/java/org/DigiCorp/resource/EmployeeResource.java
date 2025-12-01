package org.DigiCorp.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.DigiCorp.dto.EmployeeRecordDTO;
import org.DigiCorp.model.Department;
import org.DigiCorp.model.Employee;
import org.DigiCorp.service.EmployeeService;

import java.util.List;

/**
 * EmployeeResource class provides the REST endpoints as per /api/employees/
 */
@Path("/employees")
public class EmployeeResource {

    /**
     * enables us to access business logic in EmployeeService
     */
    private final EmployeeService employeeService;

    /**
     * default constructor, initializes the employee service object for use
     */
    public EmployeeResource() {
        this.employeeService = new EmployeeService();
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
        List<Department> list = employeeService.findAllDepartments();
        return Response.ok().entity(list).build();
    }

    /**
     * Endpoint #2: Get specific Employee record
     * Retrieves full employee record and related details i.e. salaries, titles, departments
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
        Employee emp = employeeService.getEmployeeRecords(empNo);
        // if the emp is null, employee does not exist & return message accordingly
        if (emp == null) {
            return Response.ok()
                    .entity("Employee record for the given employee number could not be found!")
                    .build();
        }
        // return the requested Employee
        return Response.ok().entity(emp).build();
    }

    // endpoint #3
    // usage: (1) defaulted to page 1 (2) specifying page number
    // (1) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003
    // (2) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003&page=10

    /**
     * Endpoint #3: Get paginated EmployeeDTO records by department
     * Retrieves paginated list of EmployeeDTO records belonging to some department,
     * where the requested department is supplied as an argument and each page is
     * limited to 20 entries
     * <p>
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
        // validation: check if the department is indeed valid or not
        Department department = employeeService.getDepartment(departmentNo);
        if (department == null) {
            // return 404 if invalid department was supplied
            return Response.status(404).entity("Department Number invalid!").build();
        }
        // validation: check provided page number begins from 1
        if (page < 1) {
            // returns 400 bad request for invalid page if less than 1
            return Response.status(400).entity("Page number should begin with 1!").build();
        }

        // retrieve the list of employee records
        List<EmployeeRecordDTO> empRecords = employeeService.getAllEmployeeRecordsList(departmentNo, page);

        // check the page we are at has some value
        if (empRecords.isEmpty()) {
            // if page is empty we return 200 OK but with some message
            return Response.ok().entity("Page index contains no employee records!").build();
        }

        // returns paginated list of employees from a department
        return Response.ok().entity(empRecords).build();
    }

    // endpoint #4
    @POST
    @Path("/promote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(EmployeePromotionRequest request) {
        try {
            // call your service to promote the employee
            employeeService.promoteEmployee(request);

            // return success response
            return Response.status(Response.Status.CREATED)
                    .entity("Employee promoted successfully")
                    .build();
        } catch (IllegalArgumentException e) {
            // thrown if employee not found or title is invalid
            return Response.status(Response.Status.BAD_REQUEST)
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
