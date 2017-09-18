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
package net.ponec.schulze.client.service.domain;

import java.util.Collection;
import java.util.Set;

/**
 * A preference ticket. The class have got implemented
 * {@link #equals(java.lang.Object) } and {@link #hashCode} methods.
 * @author Pavel Ponec
 */
public interface IPreference <C> {

    /** Returns all candidates in order to lower preference */
    public Collection<Set<C>> getCandidates();

    /** Quantity of the candidates */
    public int getCount();

    /** Base preference */
    public Preference<C> getBasePreference();

}
