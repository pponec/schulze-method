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
package net.ponec.schulze.client.service.method;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Comparator test
 * @author Pavel Ponec
 */
public class SchulzeComparatorTest {

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testCompare_1() {
        System.out.println("compare");

        int[][] path = new int[][] {
            {0,2},
            {1,0}
        };
        SchulzeComparator instance = new SchulzeComparator(path);
        assertTrue(instance.compare(0, 1) < 0);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testCompare_2() {
        System.out.println("compare");

        int[][] path = new int[][] {
            {0,2},
            {2,0}
        };
        SchulzeComparator instance = new SchulzeComparator(path);
        assertTrue(instance.compare(0, 1) == 0);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testCompare_3() {
        System.out.println("compare");

        int[][] path = new int[][] {
            {0,1},
            {2,0}
        };
        SchulzeComparator instance = new SchulzeComparator(path);
        assertTrue(instance.compare(0, 1) > 0);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testSorting_1() {
        System.out.println("sorting");

            int[][] path = new int[][] {
            {0,2},
            {1,0}
        };

        Integer[] candidates = new Integer[] {0,1};
        SchulzeComparator instance = new SchulzeComparator(path);
        Arrays.sort(candidates, instance);
        assertEquals((Integer)0, candidates[0]);
        assertEquals((Integer)1, candidates[1]);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testSorting_2() {
        System.out.println("sorting");

        int[][] path = new int[][] {
            {0,2},
            {2,0}
        };

        Integer[] candidates = new Integer[] {0,1};
        SchulzeComparator instance = new SchulzeComparator(path);
        Arrays.sort(candidates, instance);
        assertEquals((Integer)0, candidates[0]);
        assertEquals((Integer)1, candidates[1]);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testSorting_3() {
        System.out.println("sorting");

        int[][] path = new int[][] {
            {0,5},
            {6,0}
        };

        Integer[] candidates = new Integer[] {0,1};
        SchulzeComparator instance = new SchulzeComparator(path);
        Arrays.sort(candidates, instance);
        assertTrue(instance.compare(0, 1) > 0); // 1 < 2
        assertEquals((Integer)1, candidates[0]);
        assertEquals((Integer)0, candidates[1]);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testSorting_4() {
        System.out.println("sorting");

        int[][] path = new int[][] {
            {0,2,5},
            {0,0,0},
            {6,5,0}
        };

        Integer[] candidates = new Integer[] {0,1,2};
        SchulzeComparator instance = new SchulzeComparator(path);
        Arrays.sort(candidates, instance);

        assertTrue(instance.compare(0, 2) > 0); // 5 < 6

        assertEquals((Integer)2, candidates[0]);
        assertEquals((Integer)0, candidates[1]);
        assertEquals((Integer)1, candidates[2]);
    }

    /**
     * Test of compare method, of class SchulzeComparator.
     */
    @Test
    public void testSorting_5() {
        System.out.println("sorting");

        int[][] path = new int[][] {
            {0,5,5,5},
            {5,0,7,4},
            {5,5,0,5},
            {6,5,5,0}
        };

        Integer[] candidates = new Integer[] {0,1,2,3};
        SchulzeComparator instance = new SchulzeComparator(path);
        Arrays.sort(candidates, instance);

        assertTrue(instance.compare(0, 3) > 0); // 5 < 6

        String order = toString(candidates);
        assertTrue(order.indexOf('0') < order.indexOf('3'));
    }

    // --------- Tools ---------

    private static String toString(Integer[] c) {
        final StringBuilder result = new StringBuilder(2 * c.length);
        for (Integer i : c) {
            if (result.length() > 0) {
                result.append(',');
            }
            result.append(i);
        }
        return new String(result);
    }

}
