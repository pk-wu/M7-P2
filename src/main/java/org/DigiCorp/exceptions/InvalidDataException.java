package org.DigiCorp.exceptions;

/**
 * Custom exception class thrown when facing invalid data
 */
public class InvalidDataException extends Exception {
    /**
     * Constructs a new InvalidDataException with specific message.
     *
     * @param message detail message describing the error
     */

    private final int statusCode;


    public InvalidDataException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}