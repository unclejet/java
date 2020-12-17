package com.uj.study.design.patterns.template.method.example2.lambda;

import com.uj.study.design.patterns.template.method.example2.ApplicationDenied;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:25
 * @description：
 */
public interface Criteria {
    void check() throws ApplicationDenied;
}
