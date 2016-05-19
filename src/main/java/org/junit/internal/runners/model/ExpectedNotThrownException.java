package org.junit.internal.runners.model;

/**
 * A specific {@link AssertionError} thrown when a test is expected
 * to throw a certain exception, but it passes instead.
 *
 * @since 4.13
 *
 * @see org.junit.internal.runners.statements.ExpectException
 */
public class ExpectedNotThrownException extends AssertionError {
    private static final long serialVersionUID = -5138966477067086558L;

    public ExpectedNotThrownException(String message) {
        super(message);
    }
}
