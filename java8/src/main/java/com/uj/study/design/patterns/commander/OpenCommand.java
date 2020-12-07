package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:16
 * @description：
 */
public class OpenCommand implements Action {

    private final Editor editor;

    public OpenCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.open();
    }
}
