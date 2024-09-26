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

import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.Preference;

import java.util.*;

/**
 * Borda Count Method,
 * for more information see the <a href="https://en.wikipedia.org/wiki/Borda_count">Wikipedia</a>.
 * @author Pavel Ponec
 */
public class BordaCountVoteMethod<C> extends AbstractVoitingMethod<C> {

    /** Candidates */
    private final Map<C, Envelope> candidateMap;

    /** The number of electoral votes (number of active voters) */
    private int preferenceCount = 0;

    @SuppressWarnings("unchecked")
    public BordaCountVoteMethod(Collection<C> candidateCollection) {
        this.candidateMap = new HashMap<>(candidateCollection.size());
        for (C c : candidateCollection) {
            candidateMap.put(c, new Envelope(c));
        }
    }

    private Set<C> getTempCandidateSet() {
        final Set<C> result = new HashSet<>(candidateMap.size());
        for (Map.Entry<C, Envelope> entry : candidateMap.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }

    /** The number of electoral votes (number of active voters) */
    @Override
    public int getPreferenceCount() {
        return preferenceCount;
    }

    @Override
    public int getCandidateCount() {
        return candidateMap.size();
    }

    /** Add more votes of the same preferences */
    public void addPreference(final IPreference<C> preference) {
        if (preference == null) {
            throw new IllegalArgumentException("The preference must be defined");
        }
        this.preferenceCount += preference.getCount();
        final Set<C> tmpCandidateSet = getTempCandidateSet();
        final ArrayList<Set<C>> groupsSets = new ArrayList<>(preference.getCandidates());
        int priority = 0;
        for (int max = groupsSets.size(); priority < max; priority++) {
            final Set<C> groupSet = groupsSets.get(priority);
            for (C item : groupSet) {
                addPreference(preference, item, priority, tmpCandidateSet);
            }
        }
        for (C item : tmpCandidateSet) {
            addPreference(preference, item, priority, null);
        }
    }

    private void addPreference(IPreference<C> preference, C item, int priority,
                               Set<C> optionalCandidateSet) {
        final Envelope envelope = candidateMap.get(item);
        if (envelope == null) {
            throw new IllegalStateException("No envelope for the key '" + item +  "' was found");
        }
        envelope.add(priority * preference.getCount());
        if (optionalCandidateSet != null) {
            optionalCandidateSet.remove(item);
        }
    }

    /** Calculate a winner order */
    public Preference<C> getWinners() {
        final Preference<C> result = new Preference();
        final int[] lastSum = {-1};

        candidateMap.values().stream()
                .sorted((a, b) -> Integer.compare(a.getSum(), b.getSum()))
                .forEach(item -> {
                    result.addCandidate((C) item.c, lastSum[0] != item.getSum());
                    lastSum[0] = item.getSum();
        });

        return result;
    }

    /** Candidate envelope */
    private class Envelope implements Comparable<Envelope>{
        /** Candidate */
        final C c;
        /** Count */
        private int sum = 0;

        Envelope(final C c) {
            this.c = c;
        }

        public int getSum() {
            return sum;
        }

        public void add(int number) {
            this.sum += number;
        }

        @Override
        public int compareTo(Envelope o) {
            return Integer.compare(sum, o.sum);
        }

        @Override
        public String toString() {
            return "" + c + ":" + sum;
        }
    }
}
