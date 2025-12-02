package org.DigiCorp.helper;

import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.exceptions.InvalidDataException;

import java.util.List;

public class Helper {

    private static final List<String> DEPARTMENTS_LIST = List.of("d001", "d002", "d003", "d004", "d005", "d006", "d007", "d008", "d009");

    // convert String into TitleCase equivalent
    public static String toTitleCase(String inputString) {
        StringBuilder tempTitle = new StringBuilder();
        for (String word : inputString.split("\\s+")) {
            if (!word.isEmpty()) {
                tempTitle.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return tempTitle.toString().trim();
    }

    // helper method to check if given department exists
    public static boolean isDepartmentValid(String dept) {
        return DEPARTMENTS_LIST.contains(dept);
    }

    // helper method to validate the JSON request body
    public static void validateRequest(EmployeePromotionRequest request) throws InvalidDataException {
        // CHECK: Employee was provided
        if (request.getEmpNo() == null) {
            throw new InvalidDataException("Employee was not provided");
        }
        // CHECK: at least one value is being updated
        if (request.getNewSalary() == null && request.getNewTitle() == null && request.getNewDeptNo() == null) {
            throw new InvalidDataException("No data was supplied");
        }
        // CHECK: salary value is positive
        if (request.getNewSalary() != null && request.getNewSalary() < 1) {
            throw new InvalidDataException("Salary must be positive");
        }
        // CHECK: given department exists
        if (request.getNewDeptNo() != null && !isDepartmentValid(request.getNewDeptNo())) {
            throw new InvalidDataException("Department " + request.getNewDeptNo() + " does not exist!");
        }
        // CHECK: input title more than 0 less than 51
        if (request.getNewTitle() != null) {
            if (request.getNewTitle().isEmpty() || request.getNewTitle().length() > 50) {
                throw new InvalidDataException("New Title length invalid");
            }
        }
    }
}
