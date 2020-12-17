package com.uj.study.design.patterns.template.method.example2.lambda;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:27
 * @description：
 */
public class CompanyLoanApplication extends LoanApplication {
    public CompanyLoanApplication(Company company) {
        super(company::checkIdentity,
                company::checkProfitAndLoss,
                company::checkHistoricalDebt);
    }
}
