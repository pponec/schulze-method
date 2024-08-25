/*
 * Copyright 2024, Pavel Ponec, https://github.com/pponec/
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

import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.method.BordaCountVoteMethod;
import net.ponec.schulze.client.service.tools.ElectionUtils;
import net.ponec.schulze.client.service.tools.PlainVote;
import net.ponec.schulze.client.service.tools.SimpleTokenizer;

import java.util.Collection;
import java.util.Set;

/**
 * An election by the Schulze method
 * @author Pavel Ponec
 */
public class SummaryElection extends CommonElection<String> {

    /** Common election utils */
    protected final ElectionUtils utils = new ElectionUtils();

    /**
     * Constructor
     * @param candidates All candidates
     */
    public SummaryElection(Collection<String> candidates) {
        super(new BordaCountVoteMethod<>(candidates));
    }

    /**
     * Constructor
     * @param candidates All candidates (multiLine)
     */
    public SummaryElection(String candidates) {
        super(convert(candidates));
    }

    /** Convert String to candidates */
    private static Set<String> convert(String candidates) {
        final ElectionUtils utils = new ElectionUtils();
        return utils.createCandidates(utils.convertToPreferences(candidates));
    }

    /** Many lines type of: A-BC-DEF */
    public SummaryElection setPreferences(final String preferences) {
        final SimpleTokenizer st = new SimpleTokenizer(preferences, "\r\n");
        while (st.hasMoreElements()) {
            addPreference(st.nextElement().trim());
        }
        return this;
    }

    /** Single line type of: `A-BC-DEF` or `8:A-BC-DEF` */
    public void addPreference(final String preference) {
        PlainVote plainVote = PlainVote.of(preference);
        if (plainVote.isValid()) {
            for (int i = 0, max = plainVote.count; i < max; i++) {
                addPreference(utils.convertToPreference(plainVote.vote));
            }
        }
    }

    /** Single line type of: A-BC-DEF */
    public void addPreference(int count, String preference) {
        addPreference(count, utils.convertToPreference(preference));
    }

    // ------ Static methods -----

    /** Create election from all preferences */
    public static SummaryElection of(String preferences) {
        final ElectionUtils utils = new ElectionUtils();
        final Collection<IPreference<String>> prefs = utils.convertToPreferences(preferences);
        final Set<String> candidates = utils.createCandidates(prefs);
        final SummaryElection result = new SummaryElection(candidates);
        result.addPreferences(prefs);
        return result;
    }

}
