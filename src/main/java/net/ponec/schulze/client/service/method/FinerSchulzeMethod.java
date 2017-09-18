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
import java.util.Collection;
import java.util.Comparator;
import net.ponec.schulze.client.service.domain.Preference;

/**
 * The implementation based on Schulze method where winners have the most success in the all individual duels.
 * The order of the next candidates is derived by the number of winning duels - including fights with more successful candidates.
 * @author Pavel Ponec
 * @see https://en.wikipedia.org/wiki/Schulze_method
 */
public class FinerSchulzeMethod<C> extends SchulzeMethod<C> {

    public FinerSchulzeMethod(Collection<C> candidateCollection) {
        super(candidateCollection);
    }

    /** Method of pruning the weakest elements:
     * <br/>Candidate D is a potential winner if and only if p[D,E] ≥ p[E,D] for every other candidate E.
     * <br/>Candidate D is better than candidate E if and only if p[D,E] > p[E,D].
     * @see http://wiki.electorama.com/wiki/Schulze_method
     */
    @Override
    Preference<C> sort(final Comparator<Integer> comparator) {
        final Preference<C> result = new Preference<>();
        final Integer[] iCandidates = createCandidates();
        final int[] wins = new int[candidates.length];

        for (int i = candidates.length - 1; i >= 1; i--) {
            for (int j = i - 1; j >= 0; j--) {
                final int c = comparator.compare(i, j);
                if (c == 0) {
                    wins[i]--;
                    wins[j]--;
                } else {
                    wins[c < 0 ? i : j]--;
                }
            }
        }

        Arrays.sort(iCandidates, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                if (wins[o1] < wins[o2]) {
                    return -1;
                } else {
                    return wins[o1] > wins[o2] ? 1 : 0;
                }
            }
        });

        int lastValue = 0;
        for (int i = 0; i < iCandidates.length; i++) {
            final int j = iCandidates[i];
            final int value = wins[j];
            result.addCandidate(candidates[j], lastValue != value);
            lastValue = value;
        }

        return result;
    }

    /** Candidate number array */
    protected Integer[] createCandidates() {
        final Integer[] result = new Integer[candidates.length];
        for (int i = candidates.length - 1; i >= 0; --i) {
            result[i] = i;
        }
        return result;
    }

}
