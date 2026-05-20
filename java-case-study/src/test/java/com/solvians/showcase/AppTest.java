package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @RepeatedTest(10)
    void mainPrintsThreadsTimesQuotesLinesRepeatedly() {
        int threads = random.nextInt(1, 10);
        int quotes = random.nextInt(1, 10);
        String output = runMainCapturingStdout(String.valueOf(threads), String.valueOf(quotes));
        String[] lines = output.split("\\R");
        assertEquals(threads * quotes, lines.length);
        for (String line : lines) {
            CertificateUpdateLineAssertions.assertValidLine(line);
        }
    }

}
