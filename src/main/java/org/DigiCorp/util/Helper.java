package org.DigiCorp.util;

import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.exceptions.InvalidDataException;

/**
 * Utility class providing helper methods for common operations, such as
 * string manipulation and API request validation.
 */
public class Helper {


    /**
     * Converts the input string into Title Case
     *
     * @param inputString The string to be converted.
     * @return The string in Title Case format, with leading/trailing whitespace removed.
     */
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


    /**
     * Performs validation EmployeePromotionRequest payload.
     * Checks include ensuring all four fields (empNo, newTitle, newDeptNo, newSalary) are present (not null),
     * the salary is positive, and the title length is within acceptable bounds (1 to 50 characters).
     *
     * @param request EmployeePromotionRequest payload received from the client.
     * @throws InvalidDataException If any validation constraint is violated (e.g., missing data, non-positive salary).
     */
    public static void validateRequest(EmployeePromotionRequest request) throws InvalidDataException {
        // CHECK: ALL fields must be provided
        if (request.getEmpNo() == null ||
                request.getNewTitle() == null ||
                request.getNewDeptNo() == null ||
                request.getNewSalary() == null) {
            throw new InvalidDataException("Please provide all 4: empNo, newSalary, newTitle, newDeptNo", 400);
        }
        // CHECK: salary value is positive
        if (request.getNewSalary() < 1) {
            throw new InvalidDataException("Salary must be positive", 400);
        }

        // CHECK: input title more than 0 less than 51
        if (request.getNewTitle().isEmpty() || request.getNewTitle().length() > 50) {
            throw new InvalidDataException("New Title length invalid", 400);
        }
    }
}
