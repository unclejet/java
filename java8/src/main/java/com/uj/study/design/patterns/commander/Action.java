package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:11
 * @description：在该例子中,像 open、save 这样的操作称为命令,我们需要一个统一的接口来概括这些
 * 不同的操作,我将这个接口叫作 Action,它代表了一个操作。
 * 所有的命令都要实现该接口
 */
public interface Action {
    void perform();
}
