package com.uj.study.design.patterns.template.method.example2.tranditional;

import com.uj.study.design.patterns.template.method.example2.ApplicationDenied;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:22
 * @description：
 */
public class CompanyLoanApplication extends LoanApplication {
    @Override
    protected void checkIdentity() throws ApplicationDenied {

    }

    @Override
    protected void checkIncomeHistory() throws ApplicationDenied {

    }

    @Override
    protected void checkCreditHistory() throws ApplicationDenied {

    }
}
