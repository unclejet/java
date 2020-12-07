package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:04
 * @description：假设有一个
 * GUI Editor 组件,在上面可以执行 open、save 等一系列操作。现在我们想
 * 实现宏功能——也就是说,可以将一系列操作录制下来,日后作为一个操作执行,这就是
 * 我们的命令接收者。
 */
public interface Editor {
    void save();

    void open();

    void close();
}
