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

    @DefaultMessage("By Borda Count method")
    String byBordaCountMethod();

    @DefaultMessage("First-preference plurality")
    String byPluralityMethod();

    @DefaultMessage("Number of candidates")
    String countOfCandidates();

    @DefaultMessage("Number of valid votes")
    String countOfVotes();

    @DefaultMessage("On-line calculator (v{0}) is used to calculate the winner of the election"
            + " Schulze method where a detailed description of the method"
            + " <a target=\"_blank\" href=\"{1}\">is here</a> .")
    String helpMessage1(String version, String url);

    @DefaultMessage("How to use: create a list of candidates and assign one character to each of them." +
            " Each voter then writes their preference on one line of the form, with the character" +
            " of their biggest favourite first and the candidates with the lower preference moving to the right." +
            " The separator between candidates is a hyphen, groups are allowed. If a candidate is omitted," +
            " the voter assigns the last place to that candidate. If the voter votes for more than one person," +
            " the line may be given by the number of persons followed by a colon." +
            " More information can be found on the <a target=\"_blank\" href=\"{0}\">project homepage</a>."
)
    String helpMessage2(String homePage);

    @DefaultMessage("This version of the application only allows public voting" +
            " within a group of voters, the calculation of the results is always done on the client." +
            " The author of the calculator has paid special attention" +
            " to the verification of the correct function and has covered it with automated tests," +
            " however, he is not liable for any damage caused by its use." +
            " The user of the calculator agrees to these terms and conditions.")
    String helpMessage3();

    @DefaultMessage("Close")
    String closeButton();

}
