package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    private static final double MIN_PRICE = 100.00;
    private static final double MAX_PRICE = 200.00;
    private static final int MIN_BID_SIZE = 1000;
    private static final int MAX_BID_SIZE = 5000;
    private static final int MIN_ASK_SIZE = 1000;
    private static final int MAX_ASK_SIZE = 10000;

    private static final Pattern ISIN_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{9}[0-9]$");

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Test
    void mainRejectsMissingArgs() {
        assertThrows(RuntimeException.class, () -> App.main(new String[]{"xxx"}));
    }

    @Test
    void mainRejectsNonNumericThreads() {
        assertThrows(NumberFormatException.class, () -> App.main(new String[]{"xxx", "zzz"}));
    }

    @Test
    void mainRejectsNonNumericQuotes() {
        NumberFormatException ex = assertThrows(NumberFormatException.class, () -> App.main(new String[]{"10", "zzz"}));
        assertEquals("For input string: \"zzz\"", ex.getMessage());
    }

    @Test
    void mainPrintsThreadsTimesQuotesLines() {
        int threads = random.nextInt(1, 10);
        int quotes = random.nextInt(1, 10);
        String output = runMainCapturingStdout(String.valueOf(threads), String.valueOf(quotes));
        String[] lines = output.split("\\R");
        assertEquals(threads * quotes, lines.length);
        for (String line : lines) {
            assertValidLine(line);
        }   
    }

    // googled how to test lines printed :)
    private String runMainCapturingStdout(String threads, String quotes) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer));
            App.main(new String[]{threads, quotes});
        } finally {
            System.setOut(original);
        }
        return buffer.toString().trim();
    }

    private void assertValidLine(String line) {
        String[] fields = line.split(",", -1);
        assertEquals(6, fields.length, "line: " + line);
        assertTrue(Long.parseLong(fields[0]) > 0);
        assertTrue(ISIN_PATTERN.matcher(fields[1]).matches());
        assertValidPrice(fields[2]);
        assertValidSize(fields[3], MIN_BID_SIZE, MAX_BID_SIZE);
        assertValidPrice(fields[4]);
        assertValidSize(fields[5], MIN_ASK_SIZE, MAX_ASK_SIZE);
    }

    private void assertValidPrice(String price) {
        assertTrue(price.matches("^\\d+\\.\\d{2}$") && Double.parseDouble(price) >= MIN_PRICE && Double.parseDouble(price) <= MAX_PRICE);
    }

    private void assertValidSize(String size, int min, int max) {
        assertTrue(Integer.parseInt(size) >= min && Integer.parseInt(size) <= max);
    }

    @RepeatedTest(10)
    void mainPrintsThreadsTimesQuotesLinesRepeatedly() {
        int threads = random.nextInt(1, 10);
        int quotes = random.nextInt(1, 10);
        String output = runMainCapturingStdout(String.valueOf(threads), String.valueOf(quotes));
        String[] lines = output.split("\\R");
        assertEquals(threads * quotes, lines.length);
        for (String line : lines) {
            assertValidLine(line);
        }
    }
}
