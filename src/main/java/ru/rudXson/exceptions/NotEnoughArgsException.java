/**
 * Thrown when the program is invoked with insufficient command-line arguments.
 */
package ru.rudXson.exceptions;

public class NotEnoughArgsException extends Exception {
    /**
     * The usage string to display when this exception is caught.
     */

    /**
     * Constructs a new {@code NotEnoughArgsException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotEnoughArgsException(String message) {
        super(message);
    }

}