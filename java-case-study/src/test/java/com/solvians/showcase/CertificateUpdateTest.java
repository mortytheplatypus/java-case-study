package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateUpdateTest {

    private static final double MIN_PRICE = 100.00;
    private static final double MAX_PRICE = 200.00;
    private static final int MIN_BID_SIZE = 1000;
    private static final int MAX_BID_SIZE = 5000;
    private static final int MIN_ASK_SIZE = 1000;
    private static final int MAX_ASK_SIZE = 10000;

    private static final Pattern ISIN_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{9}[0-9]$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");

    private final CertificateUpdate certificateUpdate = new CertificateUpdate();

    @Test
    void generateCertificateUpdateMatchesReadmeExample() {
        String line = certificateUpdate.generateCertificateUpdate(
                1352122280502L,
                "DE1234567896",
                101.23,
                1000,
                103.45,
                1000);
        assertEquals("1352122280502,DE1234567896,101.23,1000,103.45,1000", line);
    }

    @Test
    void callProducesLineWithSixFields() throws Exception {
        String line = new CertificateUpdate().call();

        String[] fields = line.split(",", -1);
        assertEquals(6, fields.length);

        long timestamp = Long.parseLong(fields[0]);
        assertTrue(timestamp > 0);

        String isin = fields[1];
        assertEquals(12, isin.length());
        assertTrue(ISIN_PATTERN.matcher(isin).matches());
        String isinBase = isin.substring(0, 11);
        String checkDigit = new ISINGenerator().calculateCheckDigit(isinBase);
        assertEquals(checkDigit, String.valueOf(isin.charAt(11)));

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

    @RepeatedTest(10)
    void callRepeatedlyProducesLineWithSixFields() throws Exception {
        callProducesLineWithSixFields();
    }

}
