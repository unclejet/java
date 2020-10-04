package com.uj.study.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ：unclejet
 * @date ：Created in 2020/9/29 下午1:10
 * @description：
 * @modified By：
 * @version:
 */
public class Example1 {
    /**
     * 使用匿名内部类将行为和按钮单击进行关联
     */
    public void f1_innerClass() {
        AbstractButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("button clicked");
            }
        });
    }

    /**
     * 使用 Lambda 表达式将行为和按钮单击进行关联
     */
    public void f1_lambda() {
        AbstractButton button = new JButton();
        button.addActionListener(event -> System.out.println("button clicked"));
    }
}
