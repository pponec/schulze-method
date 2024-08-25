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


/**
 * An API for some common voiting method.
 * @author Pavel Ponec
 * @see https://en.wikipedia.org/wiki/Schulze_method
 */
public abstract class AbstractVoitingMethod<C> {

    /** The number of electoral votes (number of active voters) */
    public abstract int getPreferenceCount();

    /** The number of candidates */
    public abstract int getCandidateCount();

    /** Add more votes of the same preferences */
    public abstract void addPreference(final IPreference<C> preference);

    /** Calculate a winner order */
    public abstract Preference<C> getWinners();

  }
