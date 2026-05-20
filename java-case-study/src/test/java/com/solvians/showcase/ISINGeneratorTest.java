package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import static com.solvians.showcase.Constants.ISIN_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ISINGeneratorTest {

    private final ISINGenerator isinGenerator = new ISINGenerator();

    @Test
    void calculateCheckDigitForKnownExample() {
        assertEquals("6", isinGenerator.calculateCheckDigit("DE123456789"));
    }

    @Test
    void toNumericStringConvertsLettersUsingConversionTable() {
        assertEquals("1314123456789", isinGenerator.convertToNumericString("DE123456789"));
    }

    @Test
    void generatedISINMatchesFormatAndValidCheckDigit() {
        String isin = isinGenerator.generateRandomISIN();

        assertEquals(12, isin.length());
        assertTrue(ISIN_PATTERN.matcher(isin).matches());

        String isinBase = isin.substring(0, 11);
        String expectedCheckDigit = String.valueOf(isin.charAt(11));
        assertEquals(expectedCheckDigit, isinGenerator.calculateCheckDigit(isinBase));
    }

    @RepeatedTest(10)
    void generatedISINIsValidWithInjectedRandom() {
        String isin = isinGenerator.generateRandomISIN();
        String isinBase = isin.substring(0, 11);
        String expectedCheckDigit = String.valueOf(isin.charAt(11));
        assertEquals(expectedCheckDigit, isinGenerator.calculateCheckDigit(isinBase));
    }
}
