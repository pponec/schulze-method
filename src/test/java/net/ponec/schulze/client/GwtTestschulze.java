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
package net.ponec.schulze.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import net.ponec.schulze.shared.FieldVerifier;

/**
 * GWT JUnit <b>integration</b> tests must extend GWTTestCase.
 * Using <code>"GwtTest*"</code> naming pattern exclude them from running with
 * surefire during the test phase.
 *
 * If you run the tests using the Maven command line, you will have to
 * navigate with your browser to a specific url given by Maven.
 * See https://gwt-maven-plugin.github.io/gwt-maven-plugin/user-guide/testing.html
 * for details.
 */
public class GwtTestschulze extends GWTTestCase {

    /**
     * Must refer to a valid module that sources this class.
     */
    public String getModuleName() {
        return "net.ponec.schulze.schulzeJUnit";
    }

    /**
     * Tests the FieldVerifier.
     */
    public void testFieldVerifier() {
        assertFalse(FieldVerifier.isValidName(null));
        assertFalse(FieldVerifier.isValidName(""));
        assertFalse(FieldVerifier.isValidName("a"));
        assertFalse(FieldVerifier.isValidName("ab"));
        assertFalse(FieldVerifier.isValidName("abc"));
        assertTrue(FieldVerifier.isValidName("abcd"));
    }

    /**
     * This test will send a request to the server using the greetServer method
     * in LogService and verify the response.
     */
    public void testLogService() {
        // Create the service that we will test.
        LogServiceAsync logService = GWT.create(LogService.class);
        ServiceDefTarget target = (ServiceDefTarget) logService;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "schulze/greet");

        // Since RPC calls are asynchronous, we will need to wait for a response
        // after this test method returns. This line tells the test runner to wait
        // up to 10 seconds before timing out.
        delayTestFinish(10000);

        // Send a request to the server.
        logService.logEvent("GWT User", 0, new AsyncCallback<Boolean>() {
            @Override public void onFailure(Throwable caught) {
                // The request resulted in an unexpected error.
                fail("Request failure: " + caught.getMessage());
            }

            @Override public void onSuccess(Boolean result) {
                // Verify that the response is correct.
                assertTrue(result);

                // Now that we have received a response, we need to tell the test runner
                // that the test is complete. You must call finishTest() after an
                // asynchronous test finishes successfully, or the test will time out.
                finishTest();
            }
        });
    }

}
