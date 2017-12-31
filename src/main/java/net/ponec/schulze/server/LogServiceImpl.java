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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
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

    /** Configuration file */
    private static final Path CONFIG_FILE = Paths.get(System.getProperty("user.home"), ".config/gwtschulze/config.properties");

    /** Configuration file */
    private static final String LOG_IP_ADDRESS_PARAM = "logIpAddress";

    /** The {@code true} value */
    private static final String TRUE = "true";


    /** Log all events */
    @Override
    public boolean logEvent(String message, int size) throws IllegalArgumentException {
        final String ipAddress = logIpAddress()
                ? getThreadLocalRequest().getRemoteAddr()
                : "*";

        // Log the event:
        LOGGER.info("Calc event for input with {} characters from IP: {}"
                , size
                , ipAddress);

        return true;
    }

    /** Log IP address */
    private boolean logIpAddress() {
        boolean result = false;
        if (Files.isRegularFile(CONFIG_FILE)) {
            try {
                try (InputStream stream = Files.newInputStream(CONFIG_FILE)) {
                    final Properties config = new Properties();
                    config.load(stream);
                    result = TRUE.equals(config.getProperty(LOG_IP_ADDRESS_PARAM, null));
                }
            } catch (IOException e) {
                LOGGER.warn("Can't read configuration {}", CONFIG_FILE, e);
            }
        }
        return result;
    }
}
