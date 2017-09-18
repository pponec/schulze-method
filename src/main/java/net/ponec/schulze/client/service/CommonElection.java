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
package net.ponec.schulze.client.service;

import java.util.Collection;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.MultiPreference;
import net.ponec.schulze.client.service.domain.Preference;
import net.ponec.schulze.client.service.method.AbstractVoitingMethod;
import net.ponec.schulze.client.service.method.SchulzeMethod;

/**
 * Election using the Schulze method
 * @author Pavel Ponec
 */
public class CommonElection<C> {

    /** Election service */
    private final AbstractVoitingMethod<C> method;

    /** Special election implementation */
    public CommonElection(AbstractVoitingMethod<C> method) {
        this.method = method;
    }

    /** Required candidates */
    public CommonElection(Collection<C> candidates) {
        this(new SchulzeMethod<>(candidates));
    }

    /** Add more preferences */
    public void addPreferences(final Collection<IPreference<C>> preferences) {
        for (IPreference<C> preference : preferences) {
            addPreference(preference);
        }
    }

    /** Add single preference */
    public void addPreference(final IPreference<C> preference) {
        method.addPreference(preference);
    }

     /** Add a preference */
    public void addPreference(final int count, final Preference<C> preference) {
        addPreference(new MultiPreference<>(count, preference));
    }

    /** Count of preferences */
    public int getPreferenceCount() {
        return method.getPreferenceCount();
    }

    /** Get winners */
    public Preference<C> getWinners() {
        return method.getWinners();
    }

    /** Get Service */
    public <M extends AbstractVoitingMethod<C>> M getMethod() {
        return (M) method;
    }
}
