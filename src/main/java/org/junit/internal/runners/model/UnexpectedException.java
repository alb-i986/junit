package org.junit.internal.runners.model;

/**
 * A specific {@link AssertionError} thrown when a test is expected to throw
 * a certain exception, but it throws a different one instead.
 *
 * @since 4.13
 *
 * @see org.junit.internal.runners.statements.ExpectException
 */
public class UnexpectedException extends AssertionError {
    private static final long serialVersionUID = 7279212062084045362L;

    public UnexpectedException(String message, Throwable e) {
        super(message);
        initCause(e);
    }
}
