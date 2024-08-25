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
import java.util.List;
import net.ponec.schulze.client.service.domain.Preference;
import net.ponec.schulze.client.service.tools.ElectionUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * jUnit tests
 * @author Pavel Ponec
 */
public class SinglePlainVoteMethodTest {

    /** Common election utils */
    protected final ElectionUtils utils = new ElectionUtils();

   /**
     * Test of sort method, of class SchulzeMethod.
     */
    @Test
    public void testGetWinners() {
        System.out.println("testGetWinners");
        Collection<String> candidates = createCandidates('A','B','C');
        SingleVoteMethod<String> instance = new SingleVoteMethod<>(candidates);
        String expResult = "A-BC";

        instance.addPreference(createPreference("A-B-C"));
        instance.addPreference(createPreference("A-C-B"));
        instance.addPreference(createPreference("A-C"));
        instance.addPreference(createPreference("B-C"));
        instance.addPreference(createPreference("C-B"));
        instance.addPreference(createPreference("B-C"));
        instance.addPreference(createPreference("C-B"));

        assertEquals(3, instance.getCandidateCount());
        assertEquals(7, instance.getPreferenceCount());
        assertEquals(expResult, instance.getWinners().toString());
    }

    // ------ Helper method ------

    protected List<String> createCandidates(char ... candidates) {
        final List<String> result = new ArrayList<>(candidates.length);
        for (char candidate : candidates) {
            result.add(((Character)candidate).toString());
        }
        return result;
    }

    protected Preference<String> createPreference(String preference) {
        return utils.convertToPreference(preference);
    }

}
