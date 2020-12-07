package com.uj.study.design.patterns.commander;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/4 下午12:20
 * @description：
 */
public class Main {
    public static void main(String[] args) {
        Editor editor;

        //old school
        Macro macro = new Macro();
        macro.record(new OpenCommand(editor));
        macro.record(new SaveCommand(editor));
        macro.record(new CloseCommand(editor));
        macro.run();

        //lambda
        macro = new Macro();
        macro.record(() -> editor.open());
        macro.record(() -> editor.save());
        macro.record(() -> editor.close());
        macro.run();

        //method reference
        macro = new Macro();
        macro.record(editor::open);
        macro.record(editor::save);
        macro.record(editor::close);
        macro.run();
    }
}
