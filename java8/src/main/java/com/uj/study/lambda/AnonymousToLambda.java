package com.uj.study.lambda;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/23 上午8:58
 * @description：
 */
public class AnonymousToLambda {
    public static void f1() {
        Runnable r1 = new Runnable(){
            public void run(){
                System.out.println("Hello");
            }
        };

        Runnable r2 = () -> System.out.println("Hello");
    }

    /**
     *匿名类和Lambda表达式中的this和super的含义是不同的。在匿名类中,this代表的是类自身,但
     * 是在Lambda中,它代表的是包含类。其次,匿名类可以屏蔽包含类的变量,而Lambda表达式不
     * 能(它们会导致编译错误),譬如下面这段代码
     */
    public static void errorUse() {
        int a = 10;
        Runnable r1 = () -> {
//            int a = 2;  // 编译错误!
//            System.out.println(a);
        };

        //匿名类可以正常工作
        Runnable r2 = new Runnable() {
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
    }

    public static void doSomething(Runnable r){ r.run(); }

    public static void doSomething(Task a){ a.execute(); }

    public static void f2() {
        doSomething(new Task() {
            public void execute() {
                System.out.println("Danger danger!!");
            }
        });
    }

    /**
     * lambda表达式一定要显式指定具体使用哪个函数式接口
     */
    public static void f3() {
        doSomething((Task)() -> System.out.println("Danger danger!!"));
    }
}
