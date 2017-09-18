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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.Preference;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * The Schulze method
 * @author Pavel Ponec
 * @see https://en.wikipedia.org/wiki/Schulze_method
 */
public class SchulzeMethod<C> extends AbstractVoitingMethod<C> {

    /** Enable logs */
    private static final boolean logAllowed = false;

    /** Candidates */
    protected final C[] candidates;

    /** Matrix of pairwise preferences */
    private final int[][] d;

    /** The number of electoral votes */
    private int preferenceCount = 0;

    @SuppressWarnings("unchecked")
    public SchulzeMethod(Collection<C> candidateCollection) {
        this.candidates = (C[]) new Object[candidateCollection.size()];
        this.d = new int[candidates.length][candidates.length];

        // Fill data:
        final Set<C> validator = new HashSet<>(candidates.length);
        int i = 0;
        for (C c : candidateCollection) {
            if (validator.add(c)) {
                candidates[i++] = c;
            } else {
                throw new IllegalArgumentException("Candidate collection have got a duplicity: " + c);
            }
        }
    }

    /** The number of electoral votes */
    @Override
    public int getPreferenceCount() {
        return preferenceCount;
    }

    @Override
    public int getCandidateCount() {
        return candidates.length;
    }

    /** Add more votes of the same preferences */
    @Override
    public void addPreference(final IPreference<C> preference) {
        if (preference == null) {
            throw new IllegalArgumentException("The preference must be defined");
        }
        for (int i = 0, max = candidates.length; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (i != j) {
                    final boolean iWinner = preference.getBasePreference().compare(candidates[i], candidates[j]);
                    if (iWinner) {
                        final int[] iRow = d[i];
                        iRow[j] += preference.getCount();
                        if (logAllowed) {
                            logWiner(candidates[i], candidates[j], preference);
                        }
                    }
                }
            }
        }
        preferenceCount += preference.getCount();
    }

    /** Calculate a winner order */
    @Override
    public Preference<C> getWinners() {
        final int p[][] = strongestPath(d);
        if (logAllowed) {
            logArray(d);
            logArray(p);
        }
        final Preference<C> result = sort(new SchulzeComparator(p));
        return result;
    }

    /**
     * The Schulze method is computing the strongest path strengths
     * @param d d[i,j], the number of voters who prefer candidate i to candidate j.
     * @return [i,j], the strength of the strongest path from candidate i to candidate j.
     * @see https://en.wikipedia.org/wiki/Schulze_method
     */
    protected int[][] strongestPath(final int[][] d) {
        final int c = d.length;
        final int[][] p = new int[c][c];

        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                if (i != j) {
                    p[i][j] = d[i][j] > d[j][i] ? d[i][j] : 0;
                }
            }
        }
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                if (i != j) {
                    for (int k = 0; k < c; k++) {
                        if (i != k && j != k) {
                            p[j][k] = max(p[j][k], min(p[j][i], p[i][k]));
                        }
                    }
                }
            }
        }
        return p;
    }

    /** Sort candidates from {#link candidates} array where:
     * <br/>Candidate D is a potential winner if and only if p[D,E] ≥ p[E,D] for every other candidate E.
     * <br/>Candidate D is better than candidate E if and only if p[D,E] > p[E,D].
     * @see http://wiki.electorama.com/wiki/Schulze_method
     */
    /** Method of pruning the weakest elements */
    Preference<C> sort(final Comparator<Integer> comparator) {
        final Preference<C> result = new Preference<>();
        List<Envelope> eCandidates = createCandidates();
        List<Envelope> nextWinners = new ArrayList<>(candidates.length);

        while (eCandidates.size() > 0) {
            for (int i = eCandidates.size() - 1; i >= 1; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    final int c = comparator.compare(eCandidates.get(i).i, eCandidates.get(j).i);
                    if (c != 0) {
                        eCandidates.get(c < 0 ? j : i).win = false;
                    }
                }
            }
            boolean first = true;
            for (Envelope env : eCandidates) {
                if (env.win) {
                    result.addCandidate(env.c, first);
                    first = false;
                } else {
                    nextWinners.add(env.win());
                }
            }
            // Change two envelopes:
            final List<Envelope> tmp = eCandidates;
            eCandidates = nextWinners;
            nextWinners = tmp;
            nextWinners.clear();
        }
        return result;
    }

    private List<Envelope> createCandidates() {
        List<Envelope> envelopes = new ArrayList<>(candidates.length);
        for (int i = 0, max = candidates.length; i < max; i++) {
            envelopes.add(new Envelope(i, candidates[i], true));
        }
        return envelopes;
    }

    /** Log an array to a standard output */
    private void logArray(int[][] a) {
        final StringBuilder result = new StringBuilder();
        result.append("new int[][]{").append('\n');
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                result.append(j == 0 ? '{' : ',').append(a[i][j]);
            }
            result.append('}').append(i + 1 == a.length ? '}' : ',').append('\n');
        }
        System.out.println(result);
    }

    /** Log a winner {@code c1} to a standard output */
    private void logWiner(Object c1, Object c2, IPreference<C> preference) {
        System.out.println(c1 + " > " + c2 + " : " + preference.getBasePreference() + " : " + preference.getCount());
    }

    /** Candidate envelope */
    private class Envelope {
        final Integer i;
        final C c;
        boolean win;

        Envelope(int i, C c, boolean win) {
            this.i = i;
            this.c = c;
            this.win = win;
        }

        /** Set win to {@code true} */
        Envelope win() {
            win = true;
            return this;
        }
    }
}
