package com.uj.study.design.patterns.template.method;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午12:54
 * @description：
 */
public abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);


    // dummy Customer class
    static private class Customer {
    }

    // dummy Datbase class
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
