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
package net.ponec.schulze.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.ponec.schulze.client.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LogServiceImpl extends RemoteServiceServlet implements
        LogService {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    /** Log all events */
    @Override
    public boolean logEvent(String message, int size) throws IllegalArgumentException {

        // Log the event:
        LOGGER.info("Calc event for input with {} characters from IP: {}"
                , size
                , getThreadLocalRequest().getRemoteAddr());

        return true;
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
