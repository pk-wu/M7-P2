package org.DigiCorp.exceptions;

/**
 * Custom exception class thrown when data retrieved is empty
 */
public class EmptyResultException extends Exception {
    /**
     * Constructs a new EmptyResultException with specific message.
     *
     * @param message detail message describing the error
     */
    public EmptyResultException(String message) {
        super(message);
    }
}