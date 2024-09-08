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
package net.ponec.schulze.client;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static net.ponec.schulze.client.HtmlTools.Constants.*;

/**
 * HTML tools
 * @author Pavel Ponec
 */
public class HtmlTools {

    /** Print simple grid */
    public String printGrid(Object[][] cells) {
        return printGrid("num", cells);
    }

    /** Print simple grid */
    public String printGrid(String firstColumnStyle, Object[][] cells) {
        final Map<String,String> rightAligh = createStyle(firstColumnStyle);
        final Map<String,String> noAttribs = Collections.emptyMap();

        final StringBuilder result = new StringBuilder(256);
        printTag(TABLE, noAttribs, result);
        for (Object[] row : cells) {
            printTag(ROW, noAttribs, result);
            for (int i = 0; i < row.length; i++) {
                final Object item = row[i];
                printTag(ITEM, i == 0 ? rightAligh : noAttribs, result).append(item != null ? item : SPACE);
                printEndTag(ITEM, result);
            }
            printEndTag(ROW, result);
        }
        printEndTag(TABLE, result);
        return result.toString();
    }

    /** Create CSS stype */
    private HashMap<String, String> createStyle(final String firstColumnStyle) {
        final HashMap<String, String> result = new HashMap<>(1);
        result.put("class", firstColumnStyle);
        return result;
    }

    /** Print end tag */
    StringBuilder printEndTag(String name, StringBuilder sb) {
        return printTag(name, null, sb);
    }

    /** Print beg tag */
    StringBuilder printTag(final String name, final Map<String,String> attribs, final StringBuilder sb) {
        final boolean beg = attribs != null;
        sb.append(beg ? "<" : "</").append(name);
        if (beg) {
            for (String key : attribs.keySet()) {
                sb.append(' ').append(key).append('=').append('"').append(attribs.get(key)).append('"');
            }
        }
        sb.append('>');
        return sb;
    }

    /** Html escape */
    public String escape(Object value) {
        return SafeHtmlUtils.htmlEscape(value != null ? value.toString() : null);
    }

    abstract static class Constants {
        static final String TABLE = "table";
        static final String ROW = "tr";
        static final String ITEM = "td";
        static final String SPACE = "&nbsp;";
    }

}
