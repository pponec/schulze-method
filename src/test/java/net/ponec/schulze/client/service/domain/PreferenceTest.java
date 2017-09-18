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
package net.ponec.schulze.client.service.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pavel Ponec
 */
public class PreferenceTest {

    /**
     * Test of addCandidate method, of class Preference.
     */
    @org.junit.Test
    public void testAddCandidate() {
        System.out.println("addCandidate");

        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", true);
            //
            Collection<Set<String>> collection = instance.getCandidates();
            assertEquals(3, collection.size());
            Iterator<Set<String>> iterator = collection.iterator();
            assertEquals("A", iterator.next().iterator().next());
            assertEquals("B", iterator.next().iterator().next());
            assertEquals("C", iterator.next().iterator().next());
        }
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", false);
            instance.addCandidate("C", false);
            //
            Collection<Set<String>> collection = instance.getCandidates();
            assertEquals(1, collection.size());
            Set<String> set = collection.iterator().next();
            assertTrue(set.contains("A"));
            assertTrue(set.contains("B"));
            assertTrue(set.contains("C"));
            assertFalse(set.contains("D"));
        }
    }

    /**
     * Test of contains method, of class Preference.
     */
    @org.junit.Test
    public void testContains() {
        System.out.println("contains");
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", true);
            //
            Collection<Set<String>> collection = instance.getCandidates();
            assertEquals(3, collection.size());

            Iterator<Set<String>> iterator = collection.iterator();
            assertEquals("A", iterator.next().iterator().next());
            assertEquals("B", iterator.next().iterator().next());
            assertEquals("C", iterator.next().iterator().next());
        }
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", false);
            //
            assertEquals(2, instance.getCandidates().size());
            assertTrue(instance.contains("A"));
            assertTrue(instance.contains("B"));
            assertTrue(instance.contains("C"));
            assertFalse(instance.contains("D"));
        }
    }

    /**
     * Test of compare method, of class Preference.
     */
    @org.junit.Test
    public void testIsWinner() {
        System.out.println("isWinner");
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", false);
            //
            assertTrue(instance.compare("A", "B"));
            assertFalse(instance.compare("B", "A"));
            //
            assertTrue(instance.compare("A", "C"));
            assertFalse(instance.compare("C", "A"));
            //
            assertFalse(instance.compare("B", "C"));
            assertFalse(instance.compare("C", "B"));
        }
    }

    /**
     * Test of compare method, of class Preference.
     */
    @org.junit.Test
    public void testToString() {
        System.out.println("toString");
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", true);
            //
            assertEquals("A-B-C", instance.toString());
        }
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", true);
            instance.addCandidate("C", false);
            //
            assertEquals("A-BC", instance.toString());
        }
        {
            Preference<String> instance = new Preference<>();
            instance.addCandidate("A", true);
            instance.addCandidate("B", false);
            instance.addCandidate("C", false);
            //
            assertEquals("ABC", instance.toString());
        }
    }

    /**
     * Test of hashCode method, of class Preference.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        final int expResult = 67133779;

        final Preference<String> instance1 = new Preference<>();
        instance1.addCandidate("A1", true);
        instance1.addCandidate("A2", false);
        instance1.addCandidate("B1", true);
        instance1.addCandidate("B2", false);
        assertEquals(expResult, instance1.hashCode());

        final Preference<String> instance2 = new Preference<>();
        instance2.addCandidate("A1", true);
        instance2.addCandidate("A2", false);
        instance2.addCandidate("B1", true);
        assertNotEquals(expResult, instance2.hashCode());
        instance2.addCandidate("B2", false);
        assertEquals(expResult, instance2.hashCode());
        assertEquals(instance1.hashCode(), instance2.hashCode());

        final Preference<String> instance3 = new Preference<>();
        instance3.addCandidate("A2", true);
        instance3.addCandidate("A1", false);
        instance3.addCandidate("B2", true);
        assertNotEquals(expResult, instance3.hashCode());
        instance3.addCandidate("B1", false);
        assertEquals(expResult, instance3.hashCode());
        assertEquals(instance1.hashCode(), instance3.hashCode());
    }

    /**
     * Test of equals method, of class Preference.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        final Preference<String> instance1 = new Preference<>();
        instance1.addCandidate("A1", true);
        instance1.addCandidate("A2", false);
        instance1.addCandidate("B1", true);
        instance1.addCandidate("B2", false);
        assertEquals("A1A2-B1B2", instance1.toString());

        final Preference<String> instance2 = new Preference<>();
        instance2.addCandidate("A1", true);
        instance2.addCandidate("A2", false);
        instance2.addCandidate("B1", true);
        assertNotEquals(instance1, instance2);
        instance2.addCandidate("B2", false);
        assertEquals(instance1, instance2);
        assertEquals("A1A2-B1B2", instance2.toString());
    }

    /**
     * Test of hashCode method, of class Preference.
     */
    @Test
    public void testEqualsAndHashCode() {
        System.out.println("equalsAndHashCode");

        Set<Preference<String>> set = new HashSet<>();

        final Preference<String> instance1 = new Preference<>();
        instance1.addCandidate("A1", true);
        instance1.addCandidate("A2", false);
        instance1.addCandidate("B1", true);
        instance1.addCandidate("B2", false);
        set.add(instance1);
        set.add(instance1);

        final Preference<String> instance2 = new Preference<>();
        instance2.addCandidate("A1", true);
        instance2.addCandidate("A2", false);
        instance2.addCandidate("B1", true);
        instance2.addCandidate("B2", false);
        set.add(instance2);
        set.add(instance2);

        final Preference<String> instance3 = new Preference<>();
        instance3.addCandidate("A2", true);
        instance3.addCandidate("A1", false);
        instance3.addCandidate("B2", true);
        instance3.addCandidate("B1", false);
        set.add(instance3);
        set.add(instance3);

        assertEquals(1, set.size());
    }

}
