package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:15
 * @description：
 */
public class SaveCommand implements Action {

    private final Editor editor;

    public SaveCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.save();
    }
}
