package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:19
 * @description：
 */
public class CloseCommand implements Action {

    private final Editor editor;

    public CloseCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.close();
    }
}
