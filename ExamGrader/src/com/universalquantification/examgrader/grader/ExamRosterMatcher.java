package com.universalquantification.examgrader.grader;
import com.universalquantification.examgrader.models.Exam;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ExamRosterMatcher
{
    
    /**
     * A match result for a single form,
     * indicating the matching roster entry and the confidence level.
     *
     * @author William Chargin
     * @version 15 May 2015
     */
    public static class MatchResult
    {

        /**
         * The form matched by this result.
         */
        public final Exam form;

        /**
         * The algorithm's selection for the best matching roster entry.
         */
        public final RosterEntry match;

        /**
         * The confidence level that this match is correct,
         * from {@code 0} to {@code 1}.
         */
        public final double confidence;

        /**
         * Creates a {@code MatchResult} with the given data.
         *
         * @param form
         *      the form whose match was found
         * @param match
         *      the best guess for the roster entry
         * @param confidence
         *      the confidence level that the guess is correct
         */
        public MatchResult(Exam form, RosterEntry match, double confidence)
        {
            super();
            this.form = form;
            this.match = match;
            this.confidence = confidence;
        }

    }

    /**
     * Matches each of the given forms with one of the roster entries.
     *
     * @param form
     *      the list of forms to match
     * @param entries
     *      the candidate students who could be matched
     * @return
     *      a list of results
     */
    public static List<MatchResult> match(List<Exam> forms,
            List<RosterEntry> entries)
    {
        List<MatchResult> result = new ArrayList<>();

        // Match each form individually (no cross-referencing).
        for (Exam form : forms)
        {
            double bestWeight = Double.MAX_VALUE;
            RosterEntry bestEntry = null;

            // Consider each roster entry as a candidate.
            for (RosterEntry entry : entries)
            {
                double weight = entry.evaluateMatch(form);

                // If this is the best so far, update our tracking fields.
                if (weight < bestWeight)
                {
                    bestWeight = weight;
                    bestEntry = entry;
                }
            }

            // Compute the lengths to normalize the confidence level.
            int lfirst = Math.max(form.getStudentRecord().getStochasticFirst().length(),
                    bestEntry.getFirst().length());
            int llast = Math.max(form.getStudentRecord().getStochasticLast().length(),
                    bestEntry.getLast().length());
            double confidence = 1 - bestWeight / (lfirst + llast);

            result.add(new MatchResult(form, bestEntry, confidence));
        }

        return result;
    }

}
