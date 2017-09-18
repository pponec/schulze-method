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

import java.util.Collection;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.method.AbstractVoitingMethod;
import net.ponec.schulze.client.service.method.ElectionUtils;
import net.ponec.schulze.client.service.method.MultiVoitingMethod;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Election test
 * @author Pavel Ponec
 */
public class MultiElectionTest {

    private static final ElectionUtils utils = new ElectionUtils();

    /**
     * Test of getWinners method, of class Election.
     */
    @Test
    public void testGetWinners() {
        System.out.println("getWinners");

        CommonElection<String> instance = createElection("A-B-C");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", method.getWinnersOfSchulze().toString());
        assertEquals("A-B-C", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_2() {

        CommonElection<String> instance = createElection("C-B-A");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("C-BA", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_3() {
        CommonElection<String> instance = createElection("C-B-A");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("C-BA", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_4() {
        CommonElection<String> instance = createElection("ABC");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("ABC", method.getWinnersOfSchulze().toString());
        assertEquals("ABC", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_5() {
        CommonElection<String> instance = createElection("A-BC");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-BC", method.getWinnersOfSchulze().toString());
        assertEquals("A-BC", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_6() {
        CommonElection<String> instance = createElection(
                "C",
                "B",
                "A");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(3, instance.getPreferenceCount());
        assertEquals("CBA", method.getWinnersOfSchulze().toString());
        assertEquals("CBA", method.getWinnersOfFiner().toString());
        assertEquals("CBA", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_7() {
        CommonElection<String> instance = createElection("A-B-C");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", method.getWinnersOfSchulze().toString());
        assertEquals("A-B-C", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfSingle().toString());
    }

    @Test
    public void testGetWinners_8() {
        CommonElection<String> instance = createElection(
                "A-B-C",
                "A-C-B",
                "A-C",
                "B-C",
                "B-C",
                "C-B",
                "C-B");
        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(7, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfSingle().toString());
    }

   @Test
    public void testGetWinners_B() {
        CommonElection<String> instance = createElection(
                "A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST",
                "T-S-R-Q-PO-NMLKJIH-GFED-CBA");

        MultiVoitingMethod<String> method = instance.getMethod();
        assertEquals(2, instance.getPreferenceCount());
        assertEquals("ADEHORT-BFIJPQS-CGK-LM-N", method.getWinnersOfSchulze().toString());
        assertEquals("ADEHORT-BIJPQS-CF-K-GLM-N", method.getWinnersOfFiner().toString());
        assertEquals("AT-BCDEFGHIJKLMNOPQRS", method.getWinnersOfSingle().toString());
    }

    // ------- Help Methods -------

    private static CommonElection<String> createElection(String ... preferences) {
        return createElection(votes(preferences), true);
    }

    private static CommonElection<String> createElection(String preferences) {
        return createElection(preferences, true);
    }

    private static CommonElection<String> createElection(String preferences, boolean addPreference) {
        final Collection<IPreference<String>> prefs = utils.convertToPreferences(preferences);
        final Collection<String> candidates = utils.createCandidates(prefs);
        final AbstractVoitingMethod<String> method = new MultiVoitingMethod<>(candidates);
        final CommonElection<String> result = new CommonElection<>(method);

        if (addPreference) {
            result.addPreferences(prefs);
        }
        return result;
    }

    private static String votes(String ... vote) {
        StringBuilder result = new StringBuilder(256);
        for (String item : vote) {
            if (result.length() > 0) {
                result.append('\n');
            }
            result.append(item);
        }
        return result.toString();
    }

}
