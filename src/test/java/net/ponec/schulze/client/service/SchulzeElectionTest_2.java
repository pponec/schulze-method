/*
 * Copyright 2017, Pavel Ponec, https://github.com/pponec/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ponec.schulze.client.service;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SchulzeElection test
 * @author Pavel Ponec
 */
public class SchulzeElectionTest_2 {

    /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_01() {
        final int MAX = 10; // Test it for: * 50 voters.
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");
        instance.addPreference("A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST");
        instance.addPreference("ABC-DE-F-GH-IJ-K-LM-N-OPQ-RST");
        instance.addPreference("ABC-DEF-GH-IJ-KLM-N-OPQ-RST");
        instance.addPreference("A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST");
        instance.addPreference("AB-CDE-F-GHIJ-K-LM-N-OPQRST");
        //
        instance.addPreference("TS-RQP-ONMLKJIHGF-EDCBA");
        instance.addPreference("TSRQP-ONMLKJIHG-FEDCBA");
        instance.addPreference("T-SRQPO-NMLKJI-HGFE-C-BA");
        instance.addPreference("TSRQ-PONMLK-JIHGFED-CBA");
        instance.addPreference("T-S-R-Q-PO-NMLKJIH-GFED-CBA");

        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "AQ-BT-C-E-D-F-H-G-IJ-K-LM-N-OP-RS";
        assertEquals(expected, winners);
    }

   /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_02() {
        final int MAX = 2;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");
        instance.addPreference("A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST");
        instance.addPreference("T-S-R-Q-PO-NMLKJIH-GFED-CBA");

        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "ADEHORT-BFIJPQS-CGK-LM-N";
        String finerVal = "ADEHORT-BIJPQS-CF-K-GLM-N"; // FinerOrderMethod
        assertEquals(expected, winners);
    }

       /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_03() {
        final int MAX = 2;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");

        instance.addPreference("ABC-DE-F-GH-IJ-K-LM-N-OPQ-RST");
        instance.addPreference("TSRQ-PONMLK-JIHGFED-CBA");


        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "ABCDEKQ-FLMRST-GHN-IJOP";
        String finerVal = "ABCDEKQ-LMRST-F-GHN-IJOP"; // FinerOrderMethod
        assertEquals(expected, winners);
    }

       /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_04() {
        final int MAX = 2;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");

        instance.addPreference("ABC-DEF-GH-IJ-KLM-N-OPQ-RST");
        instance.addPreference("T-SRQPO-NMLKJI-HGFE-C-BA");


        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "CEFIJOPQT-ABGHKLMRS-DN";
        String finerVal = "CEFIJOPQT-AB-GHKLM-RS-DN"; // FinerOrderMethod
        assertEquals(expected, winners);
    }

       /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_05() {
        final int MAX = 2;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");
        instance.addPreference("AB-CDE-F-GHIJ-K-LM-N-OPQRST");
        instance.addPreference("TS-RQP-ONMLKJIHGF-EDCBA");


        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "ABFST-CDEGHIJPQR-K-LM-N-O";
        String finerVal = "ABFST-GHIJ-CDEPQR-K-LM-N-O"; // FinerOrderMethod
        assertEquals(expected, winners);
    }

       /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testWin_06() {
        final int MAX = 4;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");
        instance.addPreference("A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST");
        instance.addPreference("AB-CDE-F-GHIJ-K-LM-N-OPQRST");
        instance.addPreference("TS-RQP-ONMLKJIHGF-EDCBA");
        instance.addPreference("T-S-R-Q-PO-NMLKJIH-GFED-CBA");

        final String winners = instance.getWinners().toString(20);
        assertEquals(MAX, instance.getPreferenceCount());
        assertTrue(winners.length() > 0);
        String expected = "AT-BS-CDEPR-FQ-H-GIJ-K-LM-N-O";
        String finerVal = "AT-BS-CDEPR-Q-F-H-GIJ-K-LM-N-O"; // FinerOrderMethod
        assertEquals(expected, winners);
    }
}
