/*
 * Copyright 2017-2024, Pavel Ponec, https://github.com/pponec/
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
 * SchulzeElection test by Wiki
 * @author Pavel Ponec
 * @see https://en.wikipedia.org/wiki/Schulze_method
 */
public class SchulzeElectionWikiTest {

    /**
     * Test of getWinners method, of class SchulzeElection.
     * Example 1 (45 voters; 5 candidates):
     */
    @Test
    public void testGetWinners1() {
        System.out.println("getWikiWinners");

        SchulzeElection instance = new SchulzeElection("A-B-C-D-E");
        instance.addPreference(5, "A-C-B-E-D"); // 1
        instance.addPreference(5, "A-D-E-C-B"); // 2
        instance.addPreference(8, "B-E-D-A-C"); // 3
        instance.addPreference(3, "C-A-B-E-D"); // 4
        instance.addPreference(7, "C-A-E-B-D"); // 5
        instance.addPreference(2, "C-B-A-D-E"); // 6
        instance.addPreference(7, "D-C-E-B-A"); // 7
        instance.addPreference(8, "E-B-A-D-C"); // 8
        //
        assertEquals(5+5+8+3+7+2+7+8, instance.getPreferenceCount());
        assertEquals("E-A-C-B-D", instance.getWinners().toString());
    }

    /** Example 2 (30 voters; 4 candidates):
     * @see http://wiki.electorama.com/wiki/Schulze_method */
    @Test
    public void testGetWinners2() {
        System.out.println("getWikiWinners");

        SchulzeElection instance = new SchulzeElection("A-B-C-D");
        instance.addPreference(5, "A-C-B-D"); // 1
        instance.addPreference(2, "A-C-D-B"); // 2
        instance.addPreference(3, "A-D-C-B"); // 3
        instance.addPreference(4, "B-A-C-D"); // 4
        instance.addPreference(3, "C-B-D-A"); // 5
        instance.addPreference(3, "C-D-B-A"); // 6
        instance.addPreference(1, "D-A-C-B"); // 7
        instance.addPreference(5, "D-B-A-C"); // 8
        instance.addPreference(4, "D-C-B-A"); // 9
        //
        assertEquals(5+2+3+4+3+3+1+5+4, instance.getPreferenceCount());
        assertEquals("D-A-C-B", instance.getWinners().toString());
    }

    /** Example 3 (30 voters; 5 candidates):
     * @see http://wiki.electorama.com/wiki/Schulze_method */
    @Test
    public void testGetWinners3() {
        System.out.println("getWikiWinners");

        SchulzeElection instance = new SchulzeElection("A-B-C-D-E");
        instance.addPreference(3, "A-B-D-E-C"); // 1
        instance.addPreference(5, "A-D-E-B-C"); // 2
        instance.addPreference(1, "A-D-E-C-B"); // 3
        instance.addPreference(2, "B-A-D-E-C"); // 4
        instance.addPreference(2, "B-D-E-C-A"); // 5
        instance.addPreference(4, "C-A-B-D-E"); // 6
        instance.addPreference(6, "C-B-A-D-E"); // 7
        instance.addPreference(2, "D-B-E-C-A"); // 8
        instance.addPreference(5, "D-E-C-A-B"); // 9

        assertEquals(3+5+1+2+2+4+6+2+5, instance.getPreferenceCount());
        assertEquals("B-A-D-E-C", instance.getWinners().toString());
    }

    /** Example (9 voters; 4 candidates):
     * @see http://wiki.electorama.com/wiki/Schulze_method */
    @Test
    public void testGetWinners4() {
        System.out.println("getWikiWinners");

        SchulzeElection instance = new SchulzeElection("A-B-C-D");
        instance.addPreference(3, "A-B-C-D");
        instance.addPreference(2, "D-A-B-C");
        instance.addPreference(2, "D-B-C-A");
        instance.addPreference(2, "C-B-D-A");

        assertEquals(3+2+2+2, instance.getPreferenceCount());
        assertEquals("BD-AC", instance.getWinners().toString());
    }

}
