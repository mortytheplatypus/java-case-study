package com.solvians.showcase;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import static com.solvians.showcase.Constants.*;

public class CertificateUpdate implements Callable<String> {

    private final ISINGenerator isinGenerator;
    private final ThreadLocalRandom random;

    public CertificateUpdate() {
        this.isinGenerator = new ISINGenerator();
        this.random = ThreadLocalRandom.current();
    }

    public CertificateUpdate(ISINGenerator isinGenerator, ThreadLocalRandom random) {
        this.isinGenerator = isinGenerator;
        this.random = random;
    }

    @Override
    public String call() {
        long timestamp = System.currentTimeMillis();
        String isin = isinGenerator.generateRandomISIN();
        double bidPrice = generateRandomPrice();
        int bidSize = generateRandomIntInRange(MIN_BID_SIZE, MAX_BID_SIZE);
        double askPrice = generateRandomPrice();
        int askSize = generateRandomIntInRange(MIN_ASK_SIZE, MAX_ASK_SIZE);

        return generateCertificateUpdate(timestamp, isin, bidPrice, bidSize, askPrice, askSize);
    }

    public String generateCertificateUpdate(
            long  timestamp,
            String isin,
            double bidPrice,
            int bidSize,
            double askPrice,
            int askSize) {

        return timestamp + ","
                + isin + ","
                + String.format("%.2f", bidPrice) + ","
                + bidSize + ","
                + String.format("%.2f", askPrice) + ","
                + askSize;
    }

    private double generateRandomPrice() {
        int minPriceInt = (int) (MIN_PRICE * 100);  // 10000
        int maxPriceInt = (int) (MAX_PRICE * 100);  // 20000
        return generateRandomIntInRange(minPriceInt, maxPriceInt) / 100.0; // 100.00 - 200.00
    }

    private int generateRandomIntInRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

}
