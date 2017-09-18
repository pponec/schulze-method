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

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.LocalizableResource.Generate;

@Generate(format = "com.google.gwt.i18n.server.PropertyCatalogFactory")
@DefaultLocale("en")
public interface Messages extends com.google.gwt.i18n.client.Messages {

    @DefaultMessage("The Schulze method of voting")
    String title();

    @DefaultMessage("Author")
    String author();

    @DefaultMessage("A-BC-DEF")
    String nameField();

    @DefaultMessage("Result")
    String resultButton();

    @DefaultMessage("Save candidates")
    String candidateButton();

    @DefaultMessage("Please enter at least two characters")
    String warningTooShortPreferences();

    @DefaultMessage("Voting on-line")
    String basicVotingOnline();

    @DefaultMessage("Quick voting")
    String quickVotingOnline();

    @DefaultMessage("Help")
    String help();

    @DefaultMessage("Enter the preferences of voters per line")
    String voitingTip();

    @DefaultMessage("Enter candidates")
    String candidateTip();

    @DefaultMessage("Result of the vote")
    String voitingResult();

    @DefaultMessage("By Schulze method")
    String bySchulzeMethod();

    @DefaultMessage("Only one choice")
    String oneVotingMethod();

    @DefaultMessage("Number of candidates")
    String countOfCandidates();

    @DefaultMessage("Number of valid votes")
    String countOfVotes();

    @DefaultMessage("On-line calculator is used to calculate the winner of the election"
            + " Schulze method where a detailed description of the method"
            + " <a target=''_blank'' href=''{0}''>is here</a> .")
    String helpMessage1(String url);

    @DefaultMessage("To use: Assemble the offer candidates and assign each of them one character."
            + " Each voter then writes to form their preferences on one line, where the first give"
            + " my best and favorite of the right candidates will proceed with a lower preference."
            + " If a candidate misses a voter, he assigns it the last place.")
    String helpMessage2();

    @DefaultMessage("This version only allows the public vote within the group of voters,"
            + " the calculation result is always on the client. The author of the calculator devoted"
            + " to verify the correct function is particularly noteworthy and covered"
            + " it with automated tests, nevertheless liable for any damages caused by use."
            + " Users must agree to these terms.")
    String helpMessage3();

    @DefaultMessage("Close")
    String closeButton();

    @DefaultMessage("a")
    String a1();

    @DefaultMessage("a")
    String a2();

    @DefaultMessage("a")
    String a3();

}
