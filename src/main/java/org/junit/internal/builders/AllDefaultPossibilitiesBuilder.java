package org.junit.internal.builders;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class AllDefaultPossibilitiesBuilder extends RunnerBuilder {
    private static final JUnit4Builder JUNIT4_BUILDER = new JUnit4Builder();
    private static final JUnit3Builder JUNIT3_BUILDER = new JUnit3Builder();
    private static final IgnoredBuilder IGNORED_BUILDER = new IgnoredBuilder();
    private static final SuiteMethodBuilder SUITE_METHOD_BUILDER = new SuiteMethodBuilder();
    private static final NullBuilder NULL_BUILDER = new NullBuilder();

    private final boolean canUseSuiteMethod;

    public AllDefaultPossibilitiesBuilder(boolean canUseSuiteMethod) {
        this.canUseSuiteMethod = canUseSuiteMethod;
    }

    @Override
    public Runner runnerForClass(Class<?> testClass) throws Throwable {
        List<RunnerBuilder> builders = Arrays.asList(
                ignoredBuilder(),
                annotatedBuilder(),
                suiteMethodBuilder(),
                junit3Builder(),
                junit4Builder());

        for (RunnerBuilder each : builders) {
            Runner runner = each.safeRunnerForClass(testClass);
            if (runner != null) {
                return runner;
            }
        }
        return null;
    }

    protected JUnit4Builder junit4Builder() {
        return JUNIT4_BUILDER;
    }

    protected JUnit3Builder junit3Builder() {
        return JUNIT3_BUILDER;
    }

    protected AnnotatedBuilder annotatedBuilder() {
        return new AnnotatedBuilder(this);
    }

    protected IgnoredBuilder ignoredBuilder() {
        return IGNORED_BUILDER;
    }

    protected RunnerBuilder suiteMethodBuilder() {
        if (canUseSuiteMethod) {
            return SUITE_METHOD_BUILDER;
        }
        return NULL_BUILDER;
    }
}