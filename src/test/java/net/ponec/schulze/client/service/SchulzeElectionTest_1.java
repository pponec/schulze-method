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

import java.util.Arrays;
import net.ponec.schulze.client.service.tools.SchulzeComparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SchulzeElection test
 * @author Pavel Ponec
 */
public class SchulzeElectionTest_1 {

    /**
     * Test of getWinners method, of class SchulzeElection.
     */
    @Test
    public void testGetWinners() {
        System.out.println("getWinners");

        SchulzeElection instance = SchulzeElection.of("A-B-C");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", instance.getWinners().toString());
        assertEquals(1, instance.getPreferenceCount());
        //
        instance = SchulzeElection.of("10:A-B-C");
        assertEquals(10, instance.getPreferenceCount());
        assertEquals("A-B-C", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_2() {
        SchulzeElection instance = SchulzeElection.of("C-B-A");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_2multi() {
        String preference = "5:C-B-A";
        SchulzeElection instance = SchulzeElection.of(preference);
        instance.addPreference(preference);
        assertEquals(10, instance.getPreferenceCount());
        assertEquals("C-B-A", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_3() {
        SchulzeElection instance = new SchulzeElection("A-B-C");
        instance.addPreference("C-B-A");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_4() {
        SchulzeElection instance = SchulzeElection.of("ABC");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("ABC", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_5() {
        SchulzeElection instance = SchulzeElection.of("A-BC");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-BC", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_6() {
        SchulzeElection instance = new SchulzeElection("ABC");
        instance.addPreference("C");
        instance.addPreference("B");
        instance.addPreference("A");
        assertEquals(3, instance.getPreferenceCount());
        assertEquals("ABC", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_7() {
        SchulzeElection instance = SchulzeElection.of("A-B-C");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_8() {
        SchulzeElection instance = new SchulzeElection("ABC");
        instance.addPreference("A-B-C");
        instance.addPreference("A-C-B");
        instance.addPreference("A-C");
        instance.addPreference("B-C");
        instance.addPreference("B-C");
        instance.addPreference("C-B");
        instance.addPreference("C-B");
        //
        assertEquals(7, instance.getPreferenceCount());
        assertEquals("C-B-A", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_9() {
        SchulzeElection instance = SchulzeElection.of("A-B-C-D-E-F-G");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C-D-E-F-G", instance.getWinners().toString());
        //
        instance = SchulzeElection.of("A-B-C-D-E-F-G-X");
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C-D-E-F-G...", instance.getWinners().toString());
    }

    @Test
    public void testGetWinners_A() {
        SchulzeElection instance = new SchulzeElection(Arrays.asList("A", "B", "C"));
        instance.addPreference("B-C");
        instance.addPreference("C-B");
        //
        assertEquals(2, instance.getPreferenceCount());
        assertEquals("BC-A", instance.getWinners().toString());
    }

    /** path[
     *   0,1,1,0,0
     *   0,0,1,0,0
     *   0,0,0,0,0
     *   0,0,0,0,0
     *   0,0,0,0,0
     * ]
     * expected:<ADE-B-C> but was:<A-B-CDE>
     */
    @Test
    public void testGetWinners_B() {

        { // Sort test
            int[][] path = new int[][]{
                {0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
            };
            SchulzeComparator comparator = new SchulzeComparator(path);
            assertTrue(comparator.compare('A', 'D') == 0);
            assertTrue(comparator.compare('A', 'E') == 0);
            assertTrue(comparator.compare('E', 'C') == 0); // !!!
            //
            assertTrue(comparator.compare('A', 'E') == 0);
            assertTrue(comparator.compare('B', 'E') == 0);
            assertTrue(comparator.compare('C', 'E') == 0);
            assertTrue(comparator.compare('D', 'E') == 0);

            print(path, comparator);
        }

        final int MAX = 5;
        System.out.println(String.format("getWinners for %s candidates and %s voters", 5, MAX));

        SchulzeElection instance = new SchulzeElection("ABCDE");
        instance.addPreference("A-B-C-DE");
        instance.addPreference("ED-CBA");

        final String winners = instance.getWinners().toString();
        assertTrue(winners.length() > 0);
        String expected = "ADE-B-C";  // Result from zvolto.cz: "ADE-B-C")
        assertEquals(expected, winners); // A-B-CDE
    }

    /** Log print */
    private void print(int[][] path, SchulzeComparator comparator) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < path.length; i++) {
            for (int j = i + 1; j < path.length; j++) {
                int c = comparator.compare(i, j);
                if (c != 0) {
                    if (c < 0) {
                        result.append((char) ('A' + i)).append(" < ").append((char) ('A' + j)).append('\n');
                    } else {
                        result.append((char) ('A' + i)).append(" > ").append((char) ('A' + j)).append('\n');
                    }
                }
            }
        }
        for (int i = 0; i < path.length; i++) {
            for (int j = i + 1; j < path.length; j++) {
                int c = comparator.compare(i, j);
                if (c == 0) {
                    result.append((char) ('A' + i)).append(" = ").append((char) ('A' + j)).append('\n');
                    continue;
                }
            }
        }
        System.out.println(result);
    }
}
