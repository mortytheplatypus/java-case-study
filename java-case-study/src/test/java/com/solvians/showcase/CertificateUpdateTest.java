package com.solvians.showcase;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificateUpdateTest {

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
    void callProducesLineWithSixFields() {
        CertificateUpdateLineAssertions.assertValidLine(new CertificateUpdate().call());
    }

    @RepeatedTest(10)
    void callRepeatedlyProducesLineWithSixFields() {
        callProducesLineWithSixFields();
    }

}
