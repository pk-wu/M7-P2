package org.DigiCorp.resource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.DigiCorp.dto.EmployeeDTO;
import org.DigiCorp.model.Department;
import org.DigiCorp.model.Employee;
import org.DigiCorp.service.EmployeeService;
import org.DigiCorp.util.JPAUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/employees")
public class EmployeeResource {

    private final EmployeeService employeeService;

    // create the employeeService for us to use
    public EmployeeResource() {
        this.employeeService = new EmployeeService();
    }

    // endpoint #1
    @GET
    @Path("/getAllDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {
        List<Department> list = employeeService.findAllDepartments();
        // return the response with appropriate HTTP codes
        return Response.ok().entity(list).build();
    }

    // endpoint #2
    @GET
    @Path("/getEmployeeRecord/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeRecord(@QueryParam("empNo") int empNo) {
        EmployeeDTO emp = employeeService.getEmployeeRecords(empNo);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(emp).build();
    }

    // endpoint #3
    @GET
    @Path("/getAllEmployeeRecords/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployeeRecords(
            @QueryParam("departmentNo") String departmentNo,
            @QueryParam("page") @DefaultValue("1") int page) {

        // (info to return: employee number, first
        //name, last name and hire date)
        return Response.ok().build();
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
