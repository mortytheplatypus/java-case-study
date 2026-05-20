package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CertificateUpdateGeneratorTest {

    private static final int REPEAT_COUNT = 10;

    @Test
    void generateQuotesReturnsExpectedCount() {
        assertGenerateQuotesReturnsExpectedCount();
    }

    @RepeatedTest(REPEAT_COUNT)
    void generateQuotesReturnsExpectedCountRepeatedly() {
        assertGenerateQuotesReturnsExpectedCount();
    }

    @Test
    void generateQuotesCountIsThreadsTimesQuotes() {
        assertGenerateQuotesCountIsThreadsTimesQuotes();
    }

    @RepeatedTest(REPEAT_COUNT)
    void generateQuotesCountIsThreadsTimesQuotesRepeatedly() {
        assertGenerateQuotesCountIsThreadsTimesQuotes();
    }

    @Test
    void generateLinesReturnsSameCountAsQuotes() {
        assertGenerateLinesReturnsSameCountAsQuotes();
    }

    @RepeatedTest(REPEAT_COUNT)
    void generateLinesReturnsSameCountAsQuotesRepeatedly() {
        assertGenerateLinesReturnsSameCountAsQuotes();
    }

    @Test
    void generateLinesProducesValidLines() {
        assertGenerateLinesProducesValidLines();
    }

    @RepeatedTest(REPEAT_COUNT)
    void generateLinesProducesValidLinesRepeatedly() {
        assertGenerateLinesProducesValidLines();
    }

    @Test
    void generateQuotesCalledToProduceValidLines() throws Exception {
        assertGenerateQuotesCalledToProduceValidLines();
    }

    @RepeatedTest(REPEAT_COUNT)
    void generateQuotesCalledToProduceValidLinesRepeatedly() throws Exception {
        assertGenerateQuotesCalledToProduceValidLines();
    }

    private static void assertGenerateQuotesReturnsExpectedCount() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(10, 100);
        Stream<CertificateUpdate> quotes = generator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(10 * 100, quotes.count());
    }

    private static void assertGenerateQuotesCountIsThreadsTimesQuotes() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 3);
        assertEquals(6, generator.generateQuotes().count());
    }

    private static void assertGenerateLinesReturnsSameCountAsQuotes() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(3, 4);
        long quoteCount = generator.generateQuotes().count();
        long lineCount = generator.generateLines().count();
        assertEquals(12, quoteCount);
        assertEquals(12, lineCount);
    }

    private static void assertGenerateLinesProducesValidLines() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 2);
        generator.generateLines().forEach(CertificateUpdateLineAssertions::assertValidLine);
    }

    private static void assertGenerateQuotesCalledToProduceValidLines() throws Exception {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(1, 1);
        String line = generator.generateQuotes().findFirst().orElseThrow().call();
        CertificateUpdateLineAssertions.assertValidLine(line);
    }
}
