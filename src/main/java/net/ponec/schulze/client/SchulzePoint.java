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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SchulzePoint implements EntryPoint {

    /** Create a remote service proxy to talk to the server-side Log service. */
    private final LogServiceAsync logService = GWT.create(LogService.class);

    /** Localized messges */
    private final Messages messages = GWT.create(Messages.class);

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        Window.setTitle(messages.title());
        RootPanel.get().setTitle(messages.title());
        RootPanel.get("title").add(new InlineLabel(messages.title()));
        RootPanel.get("author").add(new InlineLabel(messages.author()));
        RootPanel.get("mainTabContainer").add(createTabPanel());
    }

    private TabPanel createTabPanel() {
        final TabPanel result = new TabPanel();
        result.add(new BasicPanel().createVotingBody(), messages.basicVotingOnline());
   //   result.add(new QuickPanel().createVotingBody(), messages.quickVotingOnline());
        result.add(createHelp(), messages.help());

        result.setWidth("500px");
        result.selectTab(0);

        return result;
    }

    /** Help message */
    private HTML createHelp() {
        String wikiUrl = "https://en.wikipedia.org/wiki/Schulze_method";
        String content
                = "<p>" + messages.helpMessage1(wikiUrl) + "</p>"
                + "<p>" + messages.helpMessage2() + "</p>"
                + "<p>" + messages.helpMessage3() + "</p>"
                ;
        return new HTML(content);
    }

}
