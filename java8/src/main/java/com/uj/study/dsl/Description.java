package com.uj.study.dsl;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/10 下午12:37
 * @description：人们通常将 DSL 分为两类:内部 DSL 和外部 DSL。外部 DSL 脱离程序源码编写,然后单
 * 独解析和实现。比如级联样式表(CSS)和正则表达式,就是常用的外部 DSL。
 * 内部 DSL 嵌入编写它们的编程语言中。如果读者使用过 JMock 和 Mockito 等模拟类库,或用
 * 过 SQL 构建 API,如 JOOQ 或 Querydsl,那么就知道什么是内部 DSL。从某种角度上说,内
 * 部 DSL 就是普通的类库,提供 API 方便使用。虽然简单,内部 DSL 却功能强大,让你的代码
 * 变得更加精炼、易读。理想情况下,使用 DSL 编写的代码读起来就像描述问题所使用的语言。
 */
public class Description {
    private Suite suite;
    public Description(String name) {

    }

    public void should(String description, Specification specification) {
        try {
            Expect expect = new Expect();
            specification.specifyBehaviour(expect);
            Runner.current.recordSuccess(suite, description);
        } catch (AssertionError cause) {
            Runner.current.recordFailure(suite, description, cause);
        } catch (Throwable cause) {
            Runner.current.recordError(suite, description, cause);
        }
    }

}
