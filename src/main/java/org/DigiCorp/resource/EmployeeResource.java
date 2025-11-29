package org.DigiCorp.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.DigiCorp.dto.EmployeeRecordDTO;
import org.DigiCorp.model.Department;
import org.DigiCorp.model.Employee;
import org.DigiCorp.service.EmployeeService;

import java.util.List;


@Path("/employees")
public class EmployeeResource {

    private final EmployeeService employeeService;

    // create the employeeService for us to use
    public EmployeeResource() {
        this.employeeService = new EmployeeService();
    }

    // endpoint #1
    // usage: http://localhost:8090/M7_P2_war_exploded/api/employees/getAllDepartments
    @GET
    @Path("/getAllDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {
        List<Department> list = employeeService.findAllDepartments();
        return Response.ok().entity(list).build();
    }

    // endpoint #2
    // usage: http://localhost:8090/M7_P2_war_exploded/api/employees/getEmployeeRecord/?empNo=99999
    @GET
    @Path("/getEmployeeRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRecord(@QueryParam("empNo") int empNo) {
        Employee emp = employeeService.getEmployeeRecords(empNo);
        if (emp == null) {
            return Response.ok()
                    .entity("Employee record for the given employee number could not be found!")
                    .build();
        }
        return Response.ok().entity(emp).build();
    }

    // endpoint #3
    // usage: (1) defaulted to page 1 (2) specifying page number
    // (1) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003
    // (2) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003&page=10
    @GET
    @Path("/getAllEmployeeRecords")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployeeRecords(
            @QueryParam("departmentNo") String departmentNo,
            @QueryParam("page") @DefaultValue("1") int page) {
        // check if the department is indeed valid or not
        Department department = employeeService.getDepartment(departmentNo);
        if (department == null) {
            return Response.status(404).entity("Department Number invalid!").build();
        }
        // check provided page number begins from 1
        if (page < 1) {
            return Response.status(400).entity("Page number should begin with 1!").build();
        }

        // retrieve the list of employee records
        List<EmployeeRecordDTO> empRecords = employeeService.getAllEmployeeRecordsList(departmentNo, page);

        // check the page we are at has some value
        if (empRecords.isEmpty()) {
            return Response.ok().entity("Page index contains no employee records!").build();
        }

        return Response.ok().entity(empRecords).build();
    }


    // endpoint #4
    @POST
    @Path("/promote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    public Response promoteEmployee(EmployeePromotionRequest request) {
    public Response promoteEmployee() {
        // call EmployeeService.promoteEmployee(request)

        return Response.status(Response.Status.CREATED).build();
    }

}
