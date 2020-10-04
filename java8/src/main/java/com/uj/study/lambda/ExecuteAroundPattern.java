package com.uj.study.lambda;

import java.io.*;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/4 上午9:56
 * @description：
 * @modified By：
 * @version:
 */
public class ExecuteAroundPattern {
    public static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(getIn())) {
            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(getIn())){
            return p.process(br);
        }

    }

    private static FileReader getIn() throws FileNotFoundException {
        String resourceName = "lambdasinaction/chap3/data.txt";
        ClassLoader classLoader = ExecuteAroundPattern.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        return new FileReader(file);
    }

    public static void main(String ...args) throws IOException{

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("---");

        //行为参数化
        String oneLine = processFile((BufferedReader b) -> b.readLine());
        System.out.println(oneLine);

        String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
        System.out.println(twoLines);

    }


    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }


}
