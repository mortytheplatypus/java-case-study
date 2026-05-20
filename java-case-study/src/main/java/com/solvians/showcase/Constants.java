package com.solvians.showcase;

import java.util.regex.Pattern;

public final class Constants {

    private Constants() {
    }

    public static final double MIN_PRICE = 100.00;
    public static final double MAX_PRICE = 200.00;
    public static final int MIN_BID_SIZE = 1000;
    public static final int MAX_BID_SIZE = 5000;
    public static final int MIN_ASK_SIZE = 1000;
    public static final int MAX_ASK_SIZE = 10000;

    public static final Pattern ISIN_PATTERN = Pattern.compile("^[A-Z]{2}[A-Z0-9]{9}[0-9]$");
    public static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");

}
