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

import java.util.Collection;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.method.AbstractVoitingMethod;
import net.ponec.schulze.client.service.tools.ElectionUtils;
import net.ponec.schulze.client.service.method.CompositeVotingMethod;
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
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", method.getWinnersOfSchulze().toString());
        assertEquals("A-B-C", method.getWinnersOfFiner().toString());
        assertEquals("A-B-C", method.getWinnersOfBorda().toString());
        assertEquals("A-BC", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_2() {

        CommonElection<String> instance = createElection("C-B-A");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("C-B-A", method.getWinnersOfBorda().toString());
        assertEquals("C-BA", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_3() {
        CommonElection<String> instance = createElection("C-B-A");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("C-B-A", method.getWinnersOfBorda().toString());
        assertEquals("C-BA", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_4() {
        CommonElection<String> instance = createElection("ABC");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("ABC", method.getWinnersOfSchulze().toString());
        assertEquals("ABC", method.getWinnersOfFiner().toString());
        assertEquals("ABC", method.getWinnersOfBorda().toString());
        assertEquals("A-BC", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_5() {
        CommonElection<String> instance = createElection("A-BC");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-BC", method.getWinnersOfSchulze().toString());
        assertEquals("A-BC", method.getWinnersOfFiner().toString());
        assertEquals("A-BC", method.getWinnersOfBorda().toString());
        assertEquals("A-BC", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_6() {
        CommonElection<String> instance = createElection(
                "C",
                "B",
                "A");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(3, instance.getPreferenceCount());
        assertEquals("CBA", method.getWinnersOfSchulze().toString());
        assertEquals("CBA", method.getWinnersOfFiner().toString());
        assertEquals("ABC", method.getWinnersOfBorda().toString());
        assertEquals("CBA", method.getWinnersOfPreferencePlurality().toString());
    }

    @Test
    public void testGetWinners_7() {
        CommonElection<String> instance = createElection("A-B-C");
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(1, instance.getPreferenceCount());
        assertEquals("A-B-C", method.getWinnersOfSchulze().toString());
        assertEquals("A-B-C", method.getWinnersOfFiner().toString());
        assertEquals("A-B-C", method.getWinnersOfBorda().toString());
        assertEquals("A-BC", method.getWinnersOfPreferencePlurality().toString());
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
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(7, instance.getPreferenceCount());
        assertEquals("C-B-A", method.getWinnersOfSchulze().toString());
        assertEquals("C-B-A", method.getWinnersOfFiner().toString());
        assertEquals("C-B-A", method.getWinnersOfBorda().toString());
        assertEquals("A-BC", method.getWinnersOfPreferencePlurality().toString());
    }

    /** Different result of the BordaCount method (!)  */
   @Test
    public void testGetWinners_B() {
        CommonElection<String> instance = createElection(
                "A-B-C-DE-F-GH-IJ-K-LM-N-O-P-QR-ST",
                "T-S-R-Q-PO-NMLKJIH-GFED-CBA");

        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(2, instance.getPreferenceCount());
        assertEquals("ADEHORT-BFIJPQS-CGK-LM-N", method.getWinnersOfSchulze().toString());
        assertEquals("ADEHORT-BIJPQS-CF-K-GLM-N", method.getWinnersOfFiner().toString());
        assertEquals("A-B-CDE-FH-GIJ-K-LMT...", method.getWinnersOfBorda().toString());
        assertEquals("AT-BCDEFGHIJKLMNOPQRS", method.getWinnersOfPreferencePlurality().toString());
    }

    /** Different result of the BordaCount method (!)  */
    @Test
    public void testGetWinners_9() {
        CommonElection<String> instance = createElection(
                "A-B-C-DE",
                "GFED-CBA");

        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(2, instance.getPreferenceCount());
        assertEquals("ADE-BGF-C", method.getWinnersOfSchulze().toString());
        assertEquals("ADE-B-CGF", method.getWinnersOfFiner().toString());
        assertEquals("A-B-CDE-FG", method.getWinnersOfBorda().toString());
        assertEquals("AG-BCDEF", method.getWinnersOfPreferencePlurality().toString());
    }

    /** Different result of the BordaCount method (!)  */
    @Test
    public void testGetWinners_10() {
        CommonElection<String> instance = createElection(
                "A-B",
                "E-AB");

        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(2, instance.getPreferenceCount());
        assertEquals("AE-B", method.getWinnersOfSchulze().toString());
        assertEquals("AE-B", method.getWinnersOfFiner().toString());
        assertEquals("A-EB", method.getWinnersOfBorda().toString());
        assertEquals("AE-B", method.getWinnersOfPreferencePlurality().toString());
    }

    /** Different result of the BordaCount method (!)
     * Sample from the <a href="https://electionbuddy.com/borda-count/">electionbuddy.com</a> page. */
    @Test
    public void testGetWinners_11() {
        String ballot = String.join("\n", ""
                        , "A-B-C-D"
                        , "C-B-D-A"
                        , "B-C-A-D"
                        , "A-C-B-D"
        );
        CommonElection<String> instance = createElection(ballot);
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(4, instance.getPreferenceCount());
        assertEquals("ABC-D", method.getWinnersOfSchulze().toString());
        assertEquals("ABC-D", method.getWinnersOfFiner().toString());
        assertEquals("BC-A-D", method.getWinnersOfBorda().toString());
        assertEquals("A-BC-D", method.getWinnersOfPreferencePlurality().toString());
    }

    /** Source: https://cs.wikipedia.org/wiki/Schulzeho_metoda#:~:text=Schulzeho%20metoda%20je%20volebn%C3%AD%20syst%C3%A9m,rozhodovalo%20jen%20mezi%20nimi%20dv%C4%9Bma.*/
    @Test
    public void wikipediaTest() {
        String ballot = String.join("\n", ""
                , "5:A-C-B-E-D"
                , "5:A-D-E-C-B"
                , "8:B-E-D-A-C"
                , "3:C-A-B-E-D"
                , "7:C-A-E-B-D"
                , "2:C-B-A-D-E"
                , "7:D-C-E-B-A"
                , "8:E-B-A-D-C"
        );
        CommonElection<String> instance = createElection(ballot);
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(45, instance.getPreferenceCount());
        assertEquals("E-A-C-B-D", method.getWinnersOfSchulze().toString());
        assertEquals("E-A-B-C-D", method.getWinnersOfBorda().toString());
        assertEquals("C-A-BE-D", method.getWinnersOfPreferencePlurality().toString());
    }

    /** The winner of the Schulze method would not even make it
     * to the second round in a majority vote. */
    @Test
    public void diplomaThesisCuni() {
        String ballot = String.join("\n", ""
                , "5:F-B-S"
                , "7:F-S-B"
                , "3:B-F-S"
                , "7:B-S-F"
                , "3:S-F-B"
                , "6:S-B-F"
        );
        CommonElection<String> instance = createElection(ballot);
        CompositeVotingMethod<String> method = instance.getMethod();
        assertEquals(31, instance.getPreferenceCount());
        assertEquals("S-B-F", method.getWinnersOfSchulze().toString());
        assertEquals("S-B-F", method.getWinnersOfBorda().toString());
        assertEquals("F-B-S", method.getWinnersOfPreferencePlurality().toString());
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
        final AbstractVoitingMethod<String> method = new CompositeVotingMethod<>(candidates);
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
