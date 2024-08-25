package net.ponec.schulze.client.service.tools;

public class PlainVote {
    private static final PlainVote INVALID = new PlainVote(0, "");

    private PlainVote(int count, String vote) {
        this.count = count;
        this.vote = vote;
    }

    public final int count;
    public final String vote;

    public boolean isValid() {
        return count > 0;
    }

    /**
     * @param textVoteParam Allowed format with a count "9:ASC-DEF"
     */
    public static PlainVote of(String textVoteParam) {
        String textVote = textVoteParam == null ? "" : textVoteParam.trim();
        int count = 1;
        try {
            final int i = textVote.indexOf(':');
            if (i > 0) {
                count = Integer.parseInt(textVote.substring(0, i));
                textVote = textVote.substring(i + 1);
            }
            return (textVote.isEmpty() || count < 1)
                    ? INVALID
                    : new PlainVote(count, textVote);
        } catch (Exception e) {
            return new PlainVote(1, textVote);
        }
    }
}