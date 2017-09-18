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

import net.ponec.schulze.client.service.method.SchulzeComparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ElectionPerformance Test
 * @author Pavel Ponec
 */
public class SchulzeElectionPerformanceTest {

    private boolean debug = false;

    /**
     * Performance for 1_000_000 voters take cca 60 second.
     */
    @Test
    public void testGetWinners() {
        final int MAX = 1000 * 1; // Test it for: * 50 voters.
        System.out.println(String.format("getWinners for %s candidates and %s voters", 20, MAX * 10));

        if (debug) {
            int[][] path = new int[][] {
            {0,2,3,6,5,5,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,3,6,5,5,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,5,4,5,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,4,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,1,0,4,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,4,4,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,1,0,4,4,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,4,4,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,3,4,4},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,3,2,0}}
            ;
            SchulzeComparator instance = new SchulzeComparator(path);
            assertTrue(instance.compare('P', 'O') == 0);
            assertTrue(instance.compare('P', 'R') < 0);
            assertTrue(instance.compare('P', 'S') < 0);
            assertTrue(instance.compare('O', 'P') == 0);
            assertTrue(instance.compare('O', 'R') == 0);
            assertTrue(instance.compare('O', 'S') == 0);
        }

        {
            SchulzeElection instance = new SchulzeElection("ABCDEFGHIJKLMNOPQRST");
            for (int i = 0; i < MAX; i++) {
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
            }

            final String winners = instance.getWinners().toString(20);
            assertEquals(MAX * 10, instance.getPreferenceCount());
            assertTrue(winners.length() > 0);
            String expected = "AQ-BT-C-E-D-F-H-G-IJ-K-LM-N-OP-RS";  // Verified from web: zvolto.cz)
            String finerVal = "AQ-BT-C-E-D-F-H-G-IJ-K-LM-N-P-O-RS"; // FinerOrderMethod
            assertEquals(expected, winners);
        }
    }
}
