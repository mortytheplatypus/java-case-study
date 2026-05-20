package com.solvians.showcase;

import java.util.Arrays;

/**
 * WSD Coding Challenge
 * Al Amru Bil Maruf
 * https://github.com/mortytheplatypus/java-case-study
 */

public class App {
    
    public App(String threads, String quotes) {

    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Expect at least number of threads and number of quotes. But got: " + Arrays.toString(args));
        }

        int threads = Integer.parseInt(args[0]);
        int quotes = Integer.parseInt(args[1]);

        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(threads, quotes);
        generator.generateLines().forEach(System.out::println);
    }
}
