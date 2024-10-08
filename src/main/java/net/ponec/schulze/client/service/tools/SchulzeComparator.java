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
package net.ponec.schulze.client.service.tools;

import java.util.Comparator;

/**
 * Schulze winner comparator
 * @author Pavel Ponec
 */
public final class SchulzeComparator implements Comparator<Integer> {

    /** The strongest path */
    final int[][] path;

    public SchulzeComparator(int[][] path) {
        this.path = path;
    }

    /** The first candidate have got the highest score.
     * @param i First value
     * @param j Second value */
    @Override
    public int compare(final Integer i, final Integer j) {
        final int ij = path[i][j];
        final int ji = path[j][i];

        if (ij > ji) {
            return -1;
        }
        return ij < ji ? 1 : 0;
    }

    /**
     * For testing only
     * @param i For example: 'A', 'B', 'C' ...
     * @param j For example: 'A', 'B', 'C' ...
     * @return
     */
    public int compare(final char i, final char j) {
        return compare(i - 'A', j - 'A');
    }
}
