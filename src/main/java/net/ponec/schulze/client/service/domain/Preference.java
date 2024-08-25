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
package net.ponec.schulze.client.service.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A preference ticket of the one voter. The class have got implemented
 * {@link #equals(java.lang.Object) } and {@link #hashCode} methods.
 * @author Pavel Ponec
 */
public class Preference <C> implements IPreference<C> {

    /** Ordered candidates */
    final private LinkedHashSet<Set<C>> candidates = new LinkedHashSet<>();

    /** The last set */
    private Set<C> lastSet = null;

    /** HashCode */
    private int hashCode = 0;

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Preference getBasePreference() {
        return this;
    }

    /**
     * Add a candidate
     * @param candidate
     * @param minorPreference A minor preference {@code true} or the same preference {@code false}
     */
    public void addCandidate(final C candidate, final boolean minorPreference) {
        if (candidate == null || candidate instanceof String && ((String)candidate).isEmpty()) {
            throw new IllegalArgumentException( "Undefined candidate is not supported");
        }
        final boolean minor = minorPreference || lastSet == null;
        if (contains(candidate)) {
            throw new IllegalArgumentException("Duplicated candidate " + candidate + " in "
                    + this
                    + (minor ? "-" : "")
                    + candidate
            );
        }
        if (minor) {
            lastSet = new LinkedHashSet<>(1);
            lastSet.add(candidate);
            candidates.add(lastSet);
        } else {
            lastSet.add(candidate);
        }
        hashCode = 0;
    }

    /** Find the candidate */
    public boolean contains(final C candidate) {
        for (Set<C> candidateSet : candidates) {
            if (candidateSet.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    /** Returns all candidates in order to lower preference */
    public Collection<Set<C>> getCandidates() {
        return candidates;
    }

    /** Find the fist candidate or {@code null}, if no candidate is preferred */
    public C getFirstCandidate() {
        for (Set<C> cands : candidates) {
            for (C c : cands) {
                return c;
            }
        }
        return null;
    }

    /** The method returns {@code true} if the first candidate wins before the second one. */
    public boolean compare(final C c1, final C c2) {
        for (Set<C> candidate : candidates) {
            if (candidate.contains(c1)) {
                return !candidate.contains(c2);
            }
            if (candidate.contains(c2)) {
                return false;
            }
        }
        return false;
    }

    /** Calculate a hash code and cache it. */
    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 7;

            for (Set<C> candidate : candidates) {
                int hashSum = 0;
                for (C c : candidate) {
                    hashSum += c.hashCode();
                }
                hashCode = 53 * hashCode + candidate.size();
                hashCode = 53 * hashCode + hashSum;
            }
        }
        return hashCode;
    }

    /** Match the current instance with another */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Preference<Object> other = (Preference<Object>) obj;
        final int otherCount = other.candidates.size();

        if (this.candidates.size() != otherCount) {
            return false;
        }

        final Iterator<Set<C>> i1 = this.candidates.iterator();
        final Iterator<Set<Object>> i2 = other.candidates.iterator();
        for (int i = otherCount; i > 0; i--) {
            final Set<C> s1 = i1.next();
            final Set<Object> s2 = i2.next();
            if (s1.size() != s2.size()) {
                return false;
            }
            for (C candidate : s1) {
                if (!s2.contains(candidate)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Print the first seven candidates */
    @Override
    public String toString() {
        return toString(7);
    }

    /** Print the first candidates by limit */
    public String toString(int groupLimit) {
        return toString("-", "", "...", groupLimit);
    }

    /** Print the first seven candidates by arguments */
    public String toString(String groupSeparator, String itemSeparator, String toBeContinued, int groupLimit) {
        final StringBuilder result = new StringBuilder();

        for (Set<C> candidate : candidates) {
            if (groupLimit-- == 0) {
                result.append("...");
                break;
            }
            if (result.length() > 0) {
                result.append(groupSeparator);
            }
            int counter = 0;
            for (C c : candidate) {
                if (0 < counter++) {
                   result.append(itemSeparator);
                }
                result.append(c);
            }
        }
        return result.toString();
    }

}
