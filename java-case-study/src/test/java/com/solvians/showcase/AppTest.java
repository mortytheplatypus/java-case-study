package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

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
        assertTrue(Constants.ISIN_PATTERN.matcher(fields[1]).matches());
        assertValidPrice(fields[2]);
        assertValidSize(fields[3], Constants.MIN_BID_SIZE, Constants.MAX_BID_SIZE);
        assertValidPrice(fields[4]);
        assertValidSize(fields[5], Constants.MIN_ASK_SIZE, Constants.MAX_ASK_SIZE);
    }

    private void assertValidPrice(String price) {
        assertTrue(price.matches("^\\d+\\.\\d{2}$") && Double.parseDouble(price) >= Constants.MIN_PRICE && Double.parseDouble(price) <= Constants.MAX_PRICE);
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
