package com.solvians.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {

    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int quotes) {
        this.threads = threads;
        this.quotes = quotes;
    }

    public Stream<CertificateUpdate> generateQuotes() {

        List<CertificateUpdate> updateList = new ArrayList<>();

        int totalUpdates = threads * quotes;

        for (int i = 0; i < totalUpdates; i++) {
            updateList.add(new CertificateUpdate());
        }
    
        return updateList.stream().parallel().limit(totalUpdates);
    }

    public Stream<String> generateLines() {
        return generateQuotes().map(this::callUpdate);
    }

    private String callUpdate(CertificateUpdate update) {
        try {
            return update.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
