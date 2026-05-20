package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CertificateUpdateGeneratorTest {

    @Test
    void generateQuotesReturnsExpectedCount() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(10, 100);
        Stream<CertificateUpdate> quotes = generator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(10 * 100, quotes.count());
    }

    @Test
    void generateQuotesCountIsThreadsTimesQuotes() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 3);
        assertEquals(6, generator.generateQuotes().count());
    }

    @Test
    void generateLinesReturnsSameCountAsQuotes() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(3, 4);
        long quoteCount = generator.generateQuotes().count();
        long lineCount = generator.generateLines().count();
        assertEquals(12, quoteCount);
        assertEquals(12, lineCount);
    }

    @Test
    void generateLinesProducesValidLines() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 2);
        generator.generateLines().forEach(CertificateUpdateLineAssertions::assertValidLine);
    }

    @Test
    void generateQuotesCalledToProduceValidLines() throws Exception {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(1, 1);
        String line = generator.generateQuotes().findFirst().orElseThrow().call();
        CertificateUpdateLineAssertions.assertValidLine(line);
    }

}
