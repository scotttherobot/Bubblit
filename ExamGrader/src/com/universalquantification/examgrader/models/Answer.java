package com.universalquantification.examgrader.models;

import java.util.Set;

/**
 * Answer represents an answer on the exam. It is comprised of a set of 
 * bubbles called choices and can be compared and by these choices. An answer
 * also has a number representing which numbered question it is the answer to
 * on a exam. An answer can also be printed for convenience.
 * 
 * @author CY Tan
 * @version 2.0
 */
public class Answer implements Comparable<Answer>
{
    /**
     * A Set of bubbles associated with this answer.
     */
    private Set<Bubble> choices;
    /**
     * Question number of the answer.
     */
    private int number;

    /**
     * Constructs an answer given a set of bubbles and a question number.
     * 
     * @param choices A set of bubbles. 
     * @param question A number representing which question this Answer is the 
     * answer to.
     */
    public Answer(Set<Bubble> choices, int question)
    {
    }

    /**
     * Compares two answers by comparing their choices.
     * 
     * @param o The answer to compare to this answers
     * @return Either -1, 0, or 1 depending on whether or not the choices 
     * between the two answers are equal and whether or not the question
     * numbers between the two answers are equal to, greater than, or less than 
     * each other.
     * @pre o is not null.
     */
    @Override
    public int compareTo(Answer o)
    {
        return 0;
    }

    /**
     * Checks if two answers are equal by comparing their choices and question
     * number.
     * 
     * @param o The answer to compare to this answers
     * @return Either true or false depending on whether or not the 
     * choices and question number between the two answers are equal.
     * @pre o is not null.
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    /**
     * Returns a hash code value for this Answer.
     * 
     * @return The hash code value for this Answer.
     */
    @Override
    public int hashCode()
    {
        return 0;
    }

    /**
     * Returns a string representation of this Answer.
     * 
     * @return A string representation of this answer. 
     */
    @Override
    public String toString()
    {
        return null;
    }
}
