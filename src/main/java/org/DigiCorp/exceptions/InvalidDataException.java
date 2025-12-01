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
    public InvalidDataException(String message) {
        super(message);
    }
}