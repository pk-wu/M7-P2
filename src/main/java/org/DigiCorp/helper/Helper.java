package org.DigiCorp.helper;

import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.exceptions.InvalidDataException;

public class Helper {


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


    // helper method to validate the JSON request body
    public static void validateRequest(EmployeePromotionRequest request) throws InvalidDataException {
        // CHECK: Employee was provided
        if (request.getEmpNo() == null) {
            throw new InvalidDataException("Employee was not provided", 400);
        }
        // CHECK: at least one value is being updated
        if (request.getNewSalary() == null && request.getNewTitle() == null && request.getNewDeptNo() == null) {
            throw new InvalidDataException("No data was supplied", 400);
        }
        // CHECK: salary value is positive
        if (request.getNewSalary() != null && request.getNewSalary() < 1) {
            throw new InvalidDataException("Salary must be positive", 400);
        }

        // CHECK: input title more than 0 less than 51
        if (request.getNewTitle() != null) {
            if (request.getNewTitle().isEmpty() || request.getNewTitle().length() > 50) {
                throw new InvalidDataException("New Title length invalid", 400);
            }
        }
    }
}
