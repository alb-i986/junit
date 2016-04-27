package org.junit.runners.model;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.*;

public class InitializationErrorTest {

    @Test
    public void testGetMessage_givenThrowableWithNoMessage() {
        InitializationError sut = new InitializationError(new ThrowableWithNoMessage());

        assertThat(sut.getMessage(), containsString("null"));
    }

    private static class ThrowableWithNoMessage extends Throwable {
        @Override
        public String getMessage() {
            return null;
        }
    }
}