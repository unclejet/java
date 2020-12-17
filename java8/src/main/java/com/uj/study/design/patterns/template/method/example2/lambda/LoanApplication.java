package com.uj.study.design.patterns.template.method.example2.lambda;

import com.uj.study.design.patterns.template.method.example2.ApplicationDenied;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:26
 * @description：
 */
public class LoanApplication {
    private final Criteria identity;
    private final Criteria creditHistory;
    private final Criteria incomeHistory;

    public LoanApplication(Criteria identity,
                           Criteria creditHistory,
                           Criteria incomeHistory) {
        this.identity = identity;
        this.creditHistory = creditHistory;
        this.incomeHistory = incomeHistory;
    }

    public void checkLoanApplication() throws ApplicationDenied {
        identity.check();
        creditHistory.check();
        incomeHistory.check();
        reportFindings();
    }

    private void reportFindings() {
// END LoanApplication

    }
}
