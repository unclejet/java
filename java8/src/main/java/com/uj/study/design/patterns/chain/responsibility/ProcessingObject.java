package com.uj.study.design.patterns.chain.responsibility;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/30 下午12:53
 * @description：
 */
public abstract class ProcessingObject<T> {
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handle(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);
}
