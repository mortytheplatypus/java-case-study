package com.solvians.showcase;

import java.util.concurrent.ThreadLocalRandom;

import static com.solvians.showcase.Constants.*;

public class ISINGenerator {

    private final ThreadLocalRandom random;

    public ISINGenerator() {
        this.random = ThreadLocalRandom.current();
    }

    public ISINGenerator(ThreadLocalRandom random) {
        this.random = random;
    }

    public String generateRandomISIN() {
        StringBuilder isin = new StringBuilder(ISIN_LENGTH);

        for (int i = 0; i < UPPERCASE_LEN_LIMIT; i++) {
            isin.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
        }

        for (int i = 0; i < ALPHANUMERIC_LEN_LIMIT; i++) {
            isin.append(ALPHANUMERIC_CHARS.charAt(random.nextInt(ALPHANUMERIC_CHARS.length())));
        }

        isin.append(calculateCheckDigit(isin.toString()));

        return isin.toString();
    }

    public String calculateCheckDigit(String isinBase) {
        String isinNumeric = convertToNumericString(isinBase);

        boolean multiply = true;
        int sum = 0;
        for (int i = isinNumeric.length() - 1; i >= 0; i--) {
            int digit = isinNumeric.charAt(i) - '0';
            if (!multiply) {
                sum += digit;
            } else {
                digit *= 2;
                sum += (digit / 10 + digit % 10);
            }
            multiply = !multiply;
        }

        int roundedUp = ((sum + 9) / 10) * 10;
        return String.valueOf(roundedUp - sum);
    }

    public String convertToNumericString(String isinBase) {
        StringBuilder numericString = new StringBuilder();

        for (int i = 0; i < isinBase.length(); i++) {
            char ch = isinBase.charAt(i);
            if (Character.isDigit(ch)) {
                numericString.append(ch);
            } else {
                numericString.append(ch - 'A' + 10);
            }
        }

        return numericString.toString();
    }

}
