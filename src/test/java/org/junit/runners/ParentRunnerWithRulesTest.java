package org.junit.runners;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ParentRunnerWithRulesTest {
    private static List<Foo> foos;

    @Test
    public void test() throws Exception {
        Foo foo1 = new Foo("foo1");
        Foo foo2 = new Foo("foo2");
        foos = Arrays.asList(foo1, foo2);

        new JUnitCore().run(Request.runner(new FooTestRunnerWithRules(SampleTestClass.class)));

        assertThat(foo1.run, is(true));
        assertThat(foo2.run, is(true));
    }

    private static class FooTestRunnerWithRules extends ParentRunnerWithRules<Foo> {

        public FooTestRunnerWithRules(Class<?> testClass) throws InitializationError {
            super(testClass);
        }

        protected Statement childStatement(final Foo child, Object test) {
            return new Statement() {
                public void evaluate() throws Throwable {
                    child.run();
                }
            };
        }

        protected List<Foo> getChildren() {
            return foos;
        }

        protected Description describeChild(Foo child) {
            return child.describe();
        }
    }

    private static class Foo {
        private final String foo;
        private boolean run = false;

        private Foo(String foo) {
            this.foo = foo;
        }

        public void run() {
            run = true;
        }

        public Description describe() {
            return Description.createTestDescription("Foo", foo);
        }
    }

    private class SampleTestClass {

        @Rule
        public TestRule rule = new TestRule() {
            public Statement apply(Statement base, Description description) {

                return base;
            }
        };
    }
}