/**
 * Thrown when the program is invoked with insufficient command-line arguments.
 */
package ru.rudXson.exceptions;

public class NotEnoughArgsException extends Exception {
    /**
     * The usage string to display when this exception is caught.
     */
    private final String usage;

    /**
     * Constructs a new {@code NotEnoughArgsException} with the specified detail message and usage string.
     *
     * @param message the detail message.
     * @param usage   the usage string.
     */
    public NotEnoughArgsException(String message, String usage) {
        super(message);
        this.usage = usage;
    }

    /**
     * Returns the usage string associated with this exception.
     *
     * @return the usage string.
     */
    public String getUsage() {
        return usage;
    }
}