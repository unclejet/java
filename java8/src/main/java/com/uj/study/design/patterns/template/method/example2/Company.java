package com.uj.study.design.patterns.template.method.example2;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:18
 * @description：
 */
public interface Company {

    // BEGIN checkSignatures
    void checkIdentity() throws ApplicationDenied;

    void checkProfitAndLoss() throws ApplicationDenied;

    void checkHistoricalDebt() throws ApplicationDenied;
    // END checkSignatures
}
