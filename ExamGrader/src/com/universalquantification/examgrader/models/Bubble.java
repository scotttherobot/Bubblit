package com.universalquantification.examgrader.models;

/**
 * Bubble represents an answer bubble. A Bubble can either be filled or not 
 * filled. A Bubble can be labeled for convenience.
 * 
 * @author CY Tan
 * @version 2.0
 */
public class Bubble
{
    private boolean isFilled;
    private String label;
    
    /**
     * Constructs a bubble given its fill condition and a label.
     * 
     * @param isFilled A boolean representing whether or not the bubble is 
     * filled.
     * @param label A string representing a value to to make it uniquely 
     * identifiable. For example, 'A', 'B', 'C', and 'D'.
     */
    public Bubble(boolean isFilled, String label) {
        
    }
    
    /**
     * Constructs a bubble given it's fill condition.
     * 
     * @param isFilled A boolean representing whether or not the bubble is 
     * filled.
     */
    public Bubble(boolean isFilled) {
        
    }
 
    /**
     * Returns whether or not the bubble is filled.
     * 
     * @return A boolean representing whether or not the bubble is filled.
     */
    public boolean isFilled() {
        return false;
    }

     /**
     * Checks if two answers are equal by comparing their choices.
     * 
     * @param o The answer to compare to this answers
     * @return Either true or false depending on whether or not the 
     * choices between the two answers are equal.
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
}
