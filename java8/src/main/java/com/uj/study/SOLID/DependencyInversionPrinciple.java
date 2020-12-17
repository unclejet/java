package com.uj.study.SOLID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/15 上午11:58
 * @description：抽象不应依赖细节,细节应该依赖抽象。
 * 具体到 Lambda 表达式,我们之前遇到的很多高阶函数都符合依赖反转原则。比如 map 函
 * 数重用了在两个集合之间转换的代码。map 函数不依赖于转换的细节,而是依赖于抽象的
 * 概念。在这里,就是依赖函数接口:Function。
 */
public class DependencyInversionPrinciple {
    public static interface HeadingFinder {
        public List<String> findHeadings(Reader reader);
    }

    //old school
    public static class NoDIP implements HeadingFinder {
        // BEGIN nodip_headings

        /**
         * 可惜,我们的代码将提取标题和资源管理、文件处理混在一起。我们真正想要的是编写提
         * 取标题的代码,而将操作文件相关的细节交给另一个方法。
         * @param input
         * @return
         */
        public List<String> findHeadings(Reader input) {
            try (BufferedReader reader = new BufferedReader(input)) {
                return reader.lines()
                        .filter(line -> line.endsWith(":"))
                        .map(line -> line.substring(0, line.length() - 1))
                        .collect(toList());
            } catch (IOException e) {
                throw new HeadingLookupException(e);
            }
        }
        // END nodip_headings
    }


    //DIP
    public static class ExtractedDIP implements HeadingFinder {
        // BEGIN refactored_headings
        public List<String> findHeadings(Reader input) {
            return withLinesOf(input,
                    lines -> lines.filter(line -> line.endsWith(":"))
                            .map(line -> line.substring(0, line.length()-1))
                            .collect(toList()),
                    HeadingLookupException::new);
        }
        // END refactored_headings

        // BEGIN with_lines_Of
        private <T> T withLinesOf(Reader input,
                                  Function<Stream<String>, T> handler,  //DIP,依赖于Function，而不是具体的资源代码
                                  Function<IOException, RuntimeException> error) {

            try (BufferedReader reader = new BufferedReader(input)) {
                return handler.apply(reader.lines());
            } catch (IOException e) {
                throw error.apply(e);
            }
        }
        // END with_lines_Of
    }
}
