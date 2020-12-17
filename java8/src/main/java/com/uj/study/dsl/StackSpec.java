package com.uj.study.dsl;

import java.util.Stack;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:39
 * @description：
 */
public class StackSpec {
    {
        describe("a stack", it -> {
            it.should("be empty when created", expect -> {
                expect.that(new Stack()).isEmpty();
            });
            it.should("push new elements onto the top of the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(1);
                expect.that(stack.get(0)).isEqualTo(1);
            });
            it.should("pop the last element pushed onto the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(2);
                stack.push(1);
                expect.that(stack.pop()).isEqualTo(2);
            });
        });
    }

    public static void describe(String name, Suite behavior) {
        Description description = new Description(name);
        behavior.specifySuite(description);
    }
}
