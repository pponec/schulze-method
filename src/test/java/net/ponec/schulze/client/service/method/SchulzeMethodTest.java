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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.ponec.schulze.client.service.domain.Preference;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * jUnit tests
 * @author Pavel Ponec
 */
public class SchulzeMethodTest {

    /**
     * Test of sort method, of class SchulzeMethod.
     */
    @Test
    public void testSort_1() {
        System.out.println("sort");
        Comparator<Integer> comparator = createComparator(true);
        Collection<Character> candidates = createCandidates('A','B','C','D');
        SchulzeMethod<Character> instance = new SchulzeMethod<>(candidates);

        String expResult = "A-B-C-D";
        Preference result = instance.sort(comparator);
        assertEquals(expResult, result.toString());
    }

    @Test
    public void testSort_2() {
        System.out.println("sort");
        Comparator<Integer> comparator = createComparator(false);
        Collection<Character> candidates = createCandidates('A','B','C','D');
        SchulzeMethod<Character> instance = new SchulzeMethod<>(candidates);

        String expResult = "D-C-B-A";
        Preference result = instance.sort(comparator);
        assertEquals(expResult, result.toString());
    }

    // ------ Helper method ------

    protected List<Character> createCandidates(char ... candidates) {
        final List<Character> result = new ArrayList<>(candidates.length);
        for (Character candidate : candidates) {
            result.add(candidate);
        }
        return result;
    }

    protected Comparator<Integer> createComparator(boolean asc) {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (o1 - o2) * (asc ? 1 : -1);
            }
        };
    }

}
