package com.uj.study.SOLID;

import java.util.stream.IntStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/14 下午12:08
 * @description：
 */
public class SingleResponsibilityPrinciple {
    /**
     * 最烂的代码
     */
    public static class ImperativeSingleMethodPrimeCounter implements PrimeCounter {
        @Override
        // BEGIN imperative_single_method
        public long countPrimes(int upTo) {
            long tally = 0;
            for (int i = 1; i < upTo; i++) {
                boolean isPrime = true;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                    }
                }
                if (isPrime) {
                    tally++;
                }
            }
            return tally;
        }
        // END imperative_single_method
    }

    /**
     * 重构了一版的代码
     */
    public static class ImperativeRefactoredPrimeCounter implements PrimeCounter {
        @Override
        // BEGIN imperative_refactored
        public long countPrimes(int upTo) {
            long tally = 0;
            for (int i = 1; i < upTo; i++) {
                if (isPrime(i)) {
                    tally++;
                }
            }
            return tally;
        }

        private boolean isPrime(int number) {
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
        // END imperative_refactored
    }

    /**
     * lambda
     */
    public static class FunctionalPrimeCounter implements PrimeCounter {

        @Override
        // BEGIN functional
        public long countPrimes(int upTo) {
            return IntStream.range(1, upTo)
                    .filter(this::isPrime)
                    .count();
        }

        private boolean isPrime(int number) {
            return IntStream.range(2, number)
                    .allMatch(x -> (number % x) != 0);
        }
        // END functional
    }

    /**
     * 并发
     */
    public static class ParallelFunctionalPrimeCounter implements PrimeCounter {
        @Override
        // BEGIN parallel_functional
        public long countPrimes(int upTo) {
            return IntStream.range(1, upTo)
                    .parallel()
                    .filter(this::isPrime)
                    .count();
        }

        private boolean isPrime(int number) {
            return IntStream.range(2, number)
                    .allMatch(x -> (number % x) != 0);
        }
        // END parallel_functional
    }
}

interface PrimeCounter {
    long countPrimes(int upTo);
}
