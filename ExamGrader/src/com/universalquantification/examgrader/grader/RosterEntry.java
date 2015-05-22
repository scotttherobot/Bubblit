package com.universalquantification.examgrader.grader;

import com.universalquantification.examgrader.models.Exam;

/**
 * An entry representing a student in the class roster.
 * Each roster entry has a unique sequence number,
 * as well as reference strings for the student's first and last names.
 *
 * @author William Chargin, Luis Cuellar
 * @version 15 May 2015
 */
public class RosterEntry
{
    // Weight the last name more than the first name, since it is more
    // likely for the last name to agree with the roster
    /**
     * The weight to assign to OCR results for first names.
     */
    public static final double kWeightFirst = 0.2;

    /**
     * The weight to assign to OCR results for last names.
     */
    public static final double kWeightLast = 0.8;

    /**
     * The sequence number for this roster entry.
     */
    private final int sequenceNumber;

    /**
     * The student's first name.
     */
    private final String first;

    /**
     * The student's last name.
     */
    private final String last;

    /**
     * Creates a {@code RosterEntry} with the given parameters.
     *
     * @param sequenceNumber
     *      the sequence number for this roster entry
     * @param first
     *      the student's first name
     * @param last
     *      the student's last name
     */
    public RosterEntry(int sequenceNumber, String first, String last)
    {
        super();
        this.sequenceNumber = sequenceNumber;
        this.first = first;
        this.last = last;
    }

    /**
     * Gets the sequence number associated with this roster entry.
     *
     * @return
     *      the associated sequence number
     */
    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    /**
     * Gets the first name of the student on this roster entry.
     *
     * @return
     *      the student's first name
     */
    public String getFirst()
    {
        return first;
    }

    /**
     * Gets the last name of the student on this roster entry.
     *
     * @return
     *      the student's last name
     */
    public String getLast()
    {
        return last;
    }

    public double evaluateMatch(Exam form)
    {
        double dfirst = form.getStudentRecord().getStochasticFirst().computeDistance(first);
        double dlast = form.getStudentRecord().getStochasticLast().computeDistance(last);
        return dfirst * kWeightFirst + dlast * kWeightLast;
    }

    @Override
    public String toString()
    {
        return "RosterEntry [seqno=" + sequenceNumber + ", first=" + first
                + ", last=" + last + "]";
    }

}
