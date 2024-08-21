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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import net.ponec.schulze.client.service.CommonElection;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.domain.MultiPreference;
import net.ponec.schulze.client.service.domain.Preference;
import net.ponec.schulze.client.service.tools.SimpleTokenizer;

/**
 * Common election utils
 * @author Pavel Ponec
 */
public class ElectionUtils {

    /** Group separator */
    public static final String SEPARATORS = "-_< ";

    /** Create new Election by preferences */
    public <T> CommonElection<T> createCommonElection(Collection<IPreference<T>> preferences) {
        final CommonElection<T> result = new CommonElection<>(createCandidates(preferences));
        for (IPreference<T> preference : preferences) {
            result.addPreference(preference);
        }
        return result;
    }

    /** Create candidates */
    public <T> Set<T> createCandidates(Collection<IPreference<T>> preferences) {
        final Set<T> result = new LinkedHashSet<>(256);
        for (IPreference<T> preference : preferences) {
            for (Set<T> candidates : preference.getCandidates()) {
                result.addAll(candidates);
            }
        }
        return result;
    }

    /**
     * Preference converter, empty lines are ignored.
     * @param preferences Multi line type of: `A-BC-DEF` or `9:A-BC-DEF`
     * @return
     */
    public Collection<IPreference<String>> convertToPreferences(String preferences) {
        final LinkedHashMap<String, MultiPreference<String>> result = new LinkedHashMap<>(512);
        final SimpleTokenizer tokenizer = new SimpleTokenizer(preferences, "\n\r");

        while (tokenizer.hasMoreElements()) {
            final String vote = tokenizer.nextElement().trim();
            if (!vote.isEmpty()) {
                final PlainVote plainVote = PlainVote.of(vote);
                final MultiPreference<String> preference = result.get(plainVote.vote);
                if (preference != null) {
                    preference.add(plainVote.count);
                } else {
                    final MultiPreference pref = MultiPreference.of(convertToPreference(vote));
                    if (plainVote.count > 1) {
                        pref.add(plainVote.count - 1);
                    }
                    result.put(vote, pref);
                }
            }
        }
        return (Collection<IPreference<String>>) (Object) result.values();
    }

    /** Create String tokenizer */
    public SimpleTokenizer createStringTokenizer(String preferences) {
        return new SimpleTokenizer(preferences, "\n\r");
    }

    /**
     * Preference converter
     * @param preference Single line type of: A-BC-DEF
     * @return
     */
    public Preference<String> convertToPreference(String preference) {
        final Preference<String> result = new Preference<>();
        final SimpleTokenizer tokenizer = new SimpleTokenizer(preference, SEPARATORS);

        while(tokenizer.hasMoreElements()) {
            String v = tokenizer.nextElement().trim();
            for (int i = 0, max = v.length(); i < max; i++) {
                char c = v.charAt(i);
                if (Character.isWhitespace(c)) {
                    continue;
                }
                result.addCandidate(String.valueOf(c), i == 0);
            }
        }
        return result;
    }

    private static class PlainVote {
        public PlainVote(int count, String vote) {
            this.count = count;
            this.vote = vote;
        }

        final int count;
        final String vote;

        /**
         * @param textVote Allowed format with a count "9:ASC-DEF"
         */
        static PlainVote of(String textVote) {
            try {
                final int i = textVote.indexOf(':');
                if (i > 0) {
                    int count = Integer.parseInt(textVote.substring(0, i));
                    String text = textVote.substring(i + 1);
                    return new PlainVote(count, text);
                } else {
                    return new PlainVote(1, textVote);
                }
            } catch (Exception e) {
                return new PlainVote(1, textVote);
            }

        }
    }

}
