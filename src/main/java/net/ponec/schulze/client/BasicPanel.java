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

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Collection;
import java.util.List;
import net.ponec.schulze.client.service.CommonElection;
import net.ponec.schulze.client.service.domain.IPreference;
import net.ponec.schulze.client.service.method.AbstractVoitingMethod;
import net.ponec.schulze.client.service.method.ElectionUtils;
import net.ponec.schulze.client.service.method.MultiVoitingMethod;
import net.ponec.schulze.shared.FieldVerifier;

/**
 * Basic voting panel
 */
public class BasicPanel {

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /** Create a remote service proxy to talk to the server-side Log service. */
    private final LogServiceAsync logService = GWT.create(LogService.class);

    /** Localized messges */
    private final Messages messages = GWT.create(Messages.class);

    /** Voting body */
    public VerticalPanel createVotingBody() {
        final VerticalPanel result = new VerticalPanel();
        result.addStyleName("votingBody");

        final TextArea voteArea = createVoteArea();
        final Button sendButton = createSendButton();
        final Label errorLabel = createErrorLabel();

        result.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
        result.add(createVoteTip());
        result.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        result.add(voteArea);
        result.add(errorLabel);
        result.add(sendButton);

        // Focus the cursor on the name field when the app loads
        voteArea.setFocus(true);
        voteArea.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        Button closeButton = createCloseButton();
        final Label textToServerLabel = createErrorLabel();

        // Winners Panel
        final HTML winnersPanel = new HTML();
        winnersPanel.setStyleName("winnersPanel");

        VerticalPanel dialogVPanel = createVerticalPanel(textToServerLabel, winnersPanel, closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {

            /** Fired when the user clicks on the sendButton. */
            @Override
            public void onClick(ClickEvent event) {
                sendNameToServer();
            }

            /** Fired when the user types in the nameField. */
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_F1) {
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                String textToServer = voteArea.getText();
                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText(messages.warningTooShortPreferences());
                    return;
                }

                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                textToServerLabel.setText("action:vote");
                winnersPanel.setText("");

                logEvent(textToServer);
            }

            protected void logEvent(String textToServer) {
                logService.logEvent(textToServer, voteArea.getText().length(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Show the RPC error message to the user
                        dialogBox.setText("Remote Failure");
                        winnersPanel.addStyleName("serverResponseLabelError");
                        winnersPanel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        final int maxLimit = 30;
                        final HtmlTools html = new HtmlTools();
                        try {
                            dialogBox.setText(messages.voitingResult());
                            winnersPanel.removeStyleName("serverResponseLabelError");
                            MultiVoitingMethod<String> res = calculateVoitingResult(voteArea.getText());
                            Object[][] dataGrid = new Object[][]{
                                {messages.bySchulzeMethod() + ':', "<strong>" + html.escape(res.getWinnersOfSchulze().toString(maxLimit)) + "</strong>"},
                                {messages.oneVotingMethod() + ':', html.escape(res.getWinnersOfSingle().toString(maxLimit)) },
                                {null, null},
                                {messages.countOfCandidates() + ':', res.getCandidateCount()},
                                {messages.countOfVotes() + ':', res.getPreferenceCount()}
                            };
                            winnersPanel.setHTML(html.printGrid(dataGrid));
                            dialogBox.center();
                            closeButton.setFocus(true);
                        } catch (Exception e) {
                            errorLabel.setText(e.getMessage());
                            sendButton.setEnabled(true);
                        }
                    }
                });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        voteArea.addKeyUpHandler(handler);

        return result;
    }

    /** Vote tip */
    protected Label createVoteTip() {
        return new Label(messages.voitingTip() + ':');
    }

    protected VerticalPanel createVerticalPanel(final Label textToServerLabel, final HTML serverResponseLabel, Button closeButton) {
        VerticalPanel result = new VerticalPanel();
        result.addStyleName("dialogVPanel");
        result.add(serverResponseLabel);
        result.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        result.add(closeButton);
        return result;
    }

    private Button createSendButton() {
        final Button result = new Button(messages.resultButton());

        // We can add style names to widgets
        result.setStyleName("sendButton btn btn-primary");

        return result;
    }

    private Label createErrorLabel() {
        final Label result = new Label();
        result.setStyleName("errorLabelContainer");
        return result;
    }

    private TextArea createVoteArea() {
        final TextArea result = new TextArea();
        result.getElement().setPropertyString("placeholder", messages.nameField());
        result.setHeight("200px");
        result.setWidth("480px");
        return result;
    }

    private Button createCloseButton() {
        final Button result = new Button(messages.closeButton());
        result.setStyleName("btn btn-primary");
        // We can set the id of a widget by accessing its Element
        result.getElement().setId("closeButton");
        return result;
    }

    /** Voiting results */
    private MultiVoitingMethod<String> calculateVoitingResult(String preferences) {
        return createMultiElection(preferences, true).getMethod();
    }

//    /** Create table */
//    private CellTable<List<String>> createGrid(String[][] rowsA) {
//
//        // Create a CellTable.
//        final CellTable<List<String>> result = new CellTable<>();
//
//        // Get the rows as List
//        int nrows = rowsA.length;
//        int ncols = rowsA[0].length;
//        ArrayList rowsL = new ArrayList(nrows);
//
//        for (int irow = 0; irow < nrows; irow++) {
//            List<String> rowL = Arrays.asList(rowsA[irow]);
//            rowsL.add(rowL);
//        }
//
//        // Create table columns
//        for (int icol = 0; icol < ncols; icol++) {
//            result.addColumn(new IndexedColumn(icol),
//                    new TextHeader(rowsA[0][icol]));
//        }
//
//        // Create a list data provider.
//        final ListDataProvider<List<String>> dataProvider = new ListDataProvider<>(rowsL);
//
//        // Add the table to the dataProvider.
//        dataProvider.addDataDisplay(result);
//
//        return result;
//    }

    private CommonElection<String> createMultiElection(String preferences, boolean addPreference) {
        final ElectionUtils utils = new ElectionUtils();
        final Collection<IPreference<String>> prefs = utils.convertToPreferences(preferences);
        final Collection<String> candidates = utils.createCandidates(prefs);
        final AbstractVoitingMethod<String> method = new MultiVoitingMethod<>(candidates);
        final CommonElection<String> result = new CommonElection<>(method);

        if (addPreference) {
            result.addPreferences(prefs);
        }
        return result;
    }

    // -------------- CLASSES -------------------

    class IndexedColumn extends Column<List<String>, String> {

        private final int index;

        public IndexedColumn(int index) {
            super(new TextCell());
            this.index = index;
        }

        @Override
        public String getValue(List<String> object) {
            return object.get(this.index);
        }
    }

}
