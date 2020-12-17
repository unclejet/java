package com.uj.study.design.patterns.template.method.example2.tranditional;

import com.uj.study.design.patterns.template.method.example2.ApplicationDenied;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:21
 * @description：
 */
public abstract class LoanApplication {
    public void checkLoanApplication() throws ApplicationDenied {
        checkIdentity();
        checkCreditHistory();
        checkIncomeHistory();
        reportFindings();
    }

    protected abstract void checkIdentity() throws ApplicationDenied;

    protected abstract void checkIncomeHistory() throws ApplicationDenied;

    protected abstract void checkCreditHistory() throws ApplicationDenied;

    private void reportFindings() {
// END LoanApplication

    }
}
