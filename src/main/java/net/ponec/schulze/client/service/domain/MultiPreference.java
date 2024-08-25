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
import java.util.Set;

/**
 * A preference ticket for a <strong>more voters<strong/>.
 * @author Pavel Ponec
 */
public final class MultiPreference<C> implements IPreference<C> {

    /** Count of the same preferences */
    private int count;
    /** Common preference */
    private final Preference<C> preference;

    public MultiPreference(final Preference<C> preference) {
        this(1, preference);
    }

    public MultiPreference(final int count, final Preference<C> preference) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }
        this.count = count;
        this.preference = preference;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Collection<Set<C>> getCandidates() {
        return preference.getCandidates();
    }

    public void add(final int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }
        this.count += count;
    }

    public void add() {
        this.count++;
    }

    @Override
    public Preference<C> getBasePreference() {
        return preference;
    }

    // ---- STATIC -----

    public static final <C> MultiPreference<C> of(IPreference<C> preference) {
        if (preference instanceof Preference) {
            return new MultiPreference<>((Preference)preference);
        }
        if (preference instanceof MultiPreference) {
            return (MultiPreference<C>) preference;
        }
        throw new IllegalArgumentException(String.valueOf(preference));
    }

}
