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
package net.ponec.schulze.client.service.method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.Preference;

/**
 * Method of the single vote per voter.
 * @author Pavel Ponec
 */
public class PreferencePluralityMethod<C> extends AbstractVoitingMethod<C> {

    /** Candidates */
    private final List<Envelope> candidates;

    /** The number of electoral votes */
    private int preferenceCount = 0;

    @SuppressWarnings("unchecked")
    public PreferencePluralityMethod(Collection<C> candidateCollection) {
        this.candidates = new ArrayList<>(candidateCollection.size());

        for (C c : candidateCollection) {
            candidates.add(new Envelope(c));
        }
    }

    /** The number of electoral votes */
    @Override
    public int getPreferenceCount() {
        return preferenceCount;
    }

    @Override
    public int getCandidateCount() {
        return candidates.size();
    }

    /** Add more votes of the same preferences */
    @Override
    public void addPreference(final IPreference<C> preference) {
        if (preference == null) {
            throw new IllegalArgumentException("The preference must be defined");
        }
        final C fistCandidate = preference.getBasePreference().getFirstCandidate();
        if (fistCandidate != null) {
            for (Envelope candidate : candidates) {
                if (candidate.c.equals(fistCandidate)) {
                    candidate.i += preference.getCount();
                    break;
                }
            }
        }
        this.preferenceCount += preference.getCount();
    }

    /** Calculate a winner order */
    public Preference<C> getWinners() {
        final Preference<C> result = new Preference();
        final List<Envelope> sorted = new ArrayList<>(candidates);
        Collections.sort(sorted, (o1, o2) -> Integer.compare(o2.i, o1.i));

        int lastCount = 0;
        for (Envelope envelope : sorted) {
            result.addCandidate(envelope.c, envelope.i != lastCount);
            lastCount = envelope.i;
        }

        return result;
    }


    /** Candidate envelope */
    private class Envelope {
        /** Candidate */
        final C c;
        /** Count */
        int i;

        Envelope(final C c) {
            this.c = c;
        }
    }
}
