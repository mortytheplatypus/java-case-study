package com.solvians.showcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CertificateUpdateLineAssertions {

    private CertificateUpdateLineAssertions() {
    }

    static void assertValidLine(String line) {
        String[] fields = line.split(",", -1);
        assertEquals(6, fields.length, "line: " + line);

        assertTrue(Long.parseLong(fields[0]) > 0);

        String isin = fields[1];
        assertEquals(12, isin.length());
        assertTrue(Constants.ISIN_PATTERN.matcher(isin).matches());

        String isinBase = isin.substring(0, 11);
        String checkDigit = new ISINGenerator().calculateCheckDigit(isinBase);
        assertEquals(checkDigit, isin.substring(11));

        assertTrue(Constants.PRICE_PATTERN.matcher(fields[2]).matches());
        assertTrue(Constants.PRICE_PATTERN.matcher(fields[4]).matches());

        int bidSize = Integer.parseInt(fields[3]);
        int askSize = Integer.parseInt(fields[5]);
        assertTrue(bidSize >= Constants.MIN_BID_SIZE && bidSize <= Constants.MAX_BID_SIZE);
        assertTrue(askSize >= Constants.MIN_ASK_SIZE && askSize <= Constants.MAX_ASK_SIZE);

        double bidPrice = Double.parseDouble(fields[2]);
        double askPrice = Double.parseDouble(fields[4]);
        assertTrue(bidPrice >= Constants.MIN_PRICE && bidPrice <= Constants.MAX_PRICE);
        assertTrue(askPrice >= Constants.MIN_PRICE && askPrice <= Constants.MAX_PRICE);
    }
}
