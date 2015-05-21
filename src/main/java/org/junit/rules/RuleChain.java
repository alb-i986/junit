package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The RuleChain rule allows ordering of TestRules. You create a
 * {@code RuleChain} with {@link #outerRule(TestRule)} and subsequent calls of
 * {@link #around(TestRule)}:
 *
 * <pre>
 * public static class UseRuleChain {
 * 	&#064;Rule
 * 	public RuleChain chain= RuleChain
 * 	                       .outerRule(new LoggingRule("outer rule")
 * 	                       .around(new LoggingRule("middle rule")
 * 	                       .around(new LoggingRule("inner rule");
 *
 * 	&#064;Test
 * 	public void example() {
 * 		assertTrue(true);
 *     }
 * }
 * </pre>
 *
 * writes the log
 *
 * <pre>
 * starting outer rule
 * starting middle rule
 * starting inner rule
 * finished inner rule
 * finished middle rule
 * finished outer rule
 * </pre>
 *
 * @since 4.10
 */
public class RuleChain implements TestRule {

    private Deque<TestRule> rulesStack;

    /**
     * Returns a {@code RuleChain} without a {@link TestRule}. This method may
     * be the starting point of a {@code RuleChain}.
     *
     * @return a {@code RuleChain} without a {@link TestRule}.
     */
    public static RuleChain emptyRuleChain() {
        return new RuleChain(new ArrayDeque<TestRule>());
    }

    /**
     * Returns a {@code RuleChain} with a single {@link TestRule}. This method
     * is the usual starting point of a {@code RuleChain}.
     *
     * @param outerRule the outer rule of the {@code RuleChain}.
     * @return a {@code RuleChain} with a single {@link TestRule}.
     */
    public static RuleChain outerRule(TestRule outerRule) {
        return emptyRuleChain().around(outerRule);
    }

    private RuleChain(Deque<TestRule> rules) {
        this.rulesStack = rules;
    }

    /**
     * Create a new {@code RuleChain}, which encloses the {@code nextRule} with
     * the rules of the current {@code RuleChain}.
     *
     * @param enclosedRule the rule to enclose.
     * @return a new {@code RuleChain}.
     */
    public RuleChain around(TestRule enclosedRule) {
        rulesStack.push(enclosedRule);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Statement apply(Statement base, Description description) {
        for (TestRule each : rulesStack) {
            base = each.apply(base, description);
        }
        return base;
    }
}