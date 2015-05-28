package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.Exam;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to match a list of {@link RosterEntry}s with a list of
 * {@link Exam}s.
 *
 * @author William Chargin, Luis Cuellar
 */
public class ExamRosterMatcher
{
    /**
    /**
     * Matches each of the given forms with one of the roster entries.
     *
     * @param forms the list of exams
     * @param entries the candidate students who could be matched
     * @return a list of results
     */
    public static List<MatchResult> match(List<Exam> forms,
            List<RosterEntry> entries)
    {
        List<MatchResult> result = new ArrayList<MatchResult>();

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
            int lfirst = Math.max(form.getStudentRecord()
                    .getStochasticFirst()
                    .length(),
                    bestEntry.getFirst().length());
            int llast = Math.max(form.getStudentRecord()
                    .getStochasticLast()
                    .length(),
                    bestEntry.getLast().length());
            double confidence = 1 - bestWeight / (lfirst + llast);

            result.add(new MatchResult(form, bestEntry, confidence));
        }

        return result;
    }

}
