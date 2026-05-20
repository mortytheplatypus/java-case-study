package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ISINGeneratorTest {

    private static final Pattern ISIN_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{9}[0-9]$");

    private ISINGenerator isinGenerator = new ISINGenerator();

    @Test
    void calculateCheckDigitForKnownExample() {
        assertEquals("6", isinGenerator.calculateCheckDigit("DE123456789"));
    }

    @Test
    void toNumericStringConvertsLettersUsingConversionTable() {
        assertEquals("1314123456789", isinGenerator.convertToNumericString("DE123456789"));
    }

    @Test
    void generatedIsinMatchesFormatAndValidCheckDigit() {
        String isin = isinGenerator.generateRandomISIN();

        assertEquals(12, isin.length());
        assertTrue(ISIN_PATTERN.matcher(isin).matches());

        String isinBase = isin.substring(0, 11);
        String expectedCheckDigit = String.valueOf(Character.getNumericValue(isin.charAt(11)));
        assertEquals(expectedCheckDigit, isinGenerator.calculateCheckDigit(isinBase));
    }

    @RepeatedTest(20)
    void generatedIsinIsValidWithInjectedRandom() {
        String isin = isinGenerator.generateRandomISIN();
        String isinBase = isin.substring(0, 11);
        String expectedCheckDigit = String.valueOf(Character.getNumericValue(isin.charAt(11)));
        assertEquals(expectedCheckDigit, isinGenerator.calculateCheckDigit(isinBase));
    }
}
