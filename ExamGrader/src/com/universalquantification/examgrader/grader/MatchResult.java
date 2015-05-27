package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.Exam;

/**
 * A match result for a single form,
 * indicating the matching roster entry and the confidence level.
 *
 * @author William Chargin
 * @version 15 May 2015
 */
public class MatchResult {

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
    public MatchResult(Exam form, RosterEntry match, double confidence) {
        super();
        this.form = form;
        this.match = match;
        
        this.form.getStudentRecord().setFirstName(this.match.getFirst());
        this.form.getStudentRecord().setlastName(this.match.getLast());
        this.confidence = confidence;
    }
}
