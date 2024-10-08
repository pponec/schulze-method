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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.Preference;

/**
 * Composite voting method
 * @author Pavel Ponec
 * @see <a href="https://en.wikipedia.org/wiki/Schulze_method">...</a>
 */
public class CompositeVotingMethod<C> extends AbstractVoitingMethod<C>{

    private final HashMap<Class<? extends AbstractVoitingMethod>, AbstractVoitingMethod> methods;

    /** The first Schulze method */
    private AbstractVoitingMethod firstMethod = null;

    public CompositeVotingMethod(Set<AbstractVoitingMethod> methods) {
        this.methods = new HashMap(methods.size());
        for (AbstractVoitingMethod method : methods) {
            addMethod(method);
        }
        checkNoEmptySize();
    }

    /** New instance for all internal methods */
    public CompositeVotingMethod(Collection<C> candidateCollection) {
        this(createFullSet(candidateCollection));
    }

    /** Create a set of internal methods */
    private static <C> Set<AbstractVoitingMethod> createFullSet(Collection<C> candidateCollection) {
        final Set<AbstractVoitingMethod> result = new LinkedHashSet<>();
        result.add(new SchulzeMethod(candidateCollection));
        result.add(new BordaCountVoteMethod(candidateCollection));
        result.add(new PreferencePluralityMethod(candidateCollection));

        return result;
    }

    /** Register a method */
    protected final void addMethod(final AbstractVoitingMethod method) throws IllegalArgumentException {
        final AbstractVoitingMethod previous = this.methods.put(method.getClass(), method);
        if (previous != null) {
            throw new IllegalArgumentException("The method type is duplicated: " + method.getClass());
        }
        if (firstMethod == null) {
            firstMethod = method;
        }
        if (method instanceof SchulzeMethod) {
            firstMethod = method;
        }
    }

    /** Check size */
    protected final void checkNoEmptySize() {
        if (this.methods.isEmpty()) {
            throw new IllegalArgumentException("Method list must not be empty");
        }
    }

    /** The number of electoral votes */
    @Override
    public int getPreferenceCount() {
        return firstMethod.getPreferenceCount();
    }

    @Override
    public int getCandidateCount() {
        return firstMethod.getCandidateCount();
    }

    /** Add more votes of the same preferences */
    @Override
    public void addPreference(final IPreference<C> preference) {
        for (AbstractVoitingMethod method : this.methods.values()) {
            method.addPreference(preference);
        }
    }

    /** Get a winners from first method */
    public Preference<C> getWinners() {
        return this.firstMethod.getWinners();
    }

        /** Get a winners from first method */
    public Preference<C> getWinners(Class<? extends AbstractVoitingMethod> type) {
        return this.methods.get(type).getWinners();
    }

    public Preference<C> getWinnersOfSchulze() {
        return getWinners(SchulzeMethod.class);
    }

    public Preference<C> getWinnersOfBorda() {
        return getWinners(BordaCountVoteMethod.class);
    }

    public Preference<C> getWinnersOfPreferencePlurality() {
        return getWinners(PreferencePluralityMethod.class);
    }

  }
