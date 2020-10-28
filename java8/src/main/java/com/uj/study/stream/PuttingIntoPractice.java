package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Trader;
import com.uj.study.model.lambdasinaction.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/16 下午1:23
 * @description：
 * @modified By：
 * @version:
 */
public class PuttingIntoPractice {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    /**
     * 找出2011年的所有交易并按交易额排序(从低到高)
     */
    public void q1() {
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);
    }

    /**
     * 交易员都在哪些不同的城市工作过
     */
    public void q2() {
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(cities);
    }

    /**
     * 查找所有来自于剑桥的交易员,并按姓名排序
     */
    public void q3() {
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println(traders);
    }

    /**
     * 返回所有交易员的姓名字符串,按字母顺序排序
     */
    public void q4() {
        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);
    }

    /**
     * 有没有交易员是在米兰工作的
     */
    public void q5() {
        boolean milanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader()
                        .getCity().equals("Milan")
                );
        System.out.println(milanBased);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额
     */
    public void q6() {
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * 所有交易中,最高的交易额是多少
     */
    public void q7() {
        int highestValue = transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);
    }

    /**
     * 找到交易额最小的交易
     */
    public void q8() {
        Optional<Transaction> smallestTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

    /**
     * Update all transactions so that the traders from Milan are set to Cambridge.
     */
    public void q9() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .forEach(trader -> trader.setCity("Cambridge"));
        System.out.println(transactions);
    }

    /**
     * output
     * filtering 1
     * filtering 2
     * mapping 2
     * filtering 3
     * filtering 4
     * mapping 4
     * 4
     * 16
     */
    public static void laziness() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {
                            System.out.println("filtering " + n); return n % 2 == 0;
                        })
                        .map(n -> {
                            System.out.println("mapping " + n);
                            return n * n;
                        })
                        .limit(2)
                        .collect(toList());
        twoEvenSquares.forEach(t->System.out.println(t));
    }

    public static void main(String[] args) {
//        laziness();
    }
}
