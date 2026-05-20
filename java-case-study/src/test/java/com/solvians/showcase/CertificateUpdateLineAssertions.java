package com.solvians.showcase;

import static com.solvians.showcase.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CertificateUpdateLineAssertions {

    private CertificateUpdateLineAssertions() {
    }

    static void assertValidLine(String line) {
        String[] fields = line.split(",", -1);
        assertEquals(6, fields.length);

        assertTrue(Long.parseLong(fields[0]) > 0);

        String isin = fields[1];
        assertEquals(12, isin.length());
        assertTrue(ISIN_PATTERN.matcher(isin).matches());

        String isinBase = isin.substring(0, 11);
        String checkDigit = new ISINGenerator().calculateCheckDigit(isinBase);
        assertEquals(checkDigit, isin.substring(11));

        assertTrue(PRICE_PATTERN.matcher(fields[2]).matches());
        assertTrue(PRICE_PATTERN.matcher(fields[4]).matches());

        int bidSize = Integer.parseInt(fields[3]);
        int askSize = Integer.parseInt(fields[5]);
        assertTrue(bidSize >= MIN_BID_SIZE && bidSize <= MAX_BID_SIZE);
        assertTrue(askSize >= MIN_ASK_SIZE && askSize <= MAX_ASK_SIZE);

        double bidPrice = Double.parseDouble(fields[2]);
        double askPrice = Double.parseDouble(fields[4]);
        assertTrue(bidPrice >= MIN_PRICE && bidPrice <= MAX_PRICE);
        assertTrue(askPrice >= MIN_PRICE && askPrice <= MAX_PRICE);
    }
}
