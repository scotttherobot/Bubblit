package com.universalquantification.examgrader.models;

import java.util.Objects;

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
        // SET the field isFilled to isFilled
        // SET the field label to label
        
        this.isFilled = isFilled;
        this.label = label;
    }
    
    /**
     * Constructs a bubble given it's fill condition.
     * 
     * @param isFilled A boolean representing whether or not the bubble is 
     * filled.
     */
    public Bubble(boolean isFilled) {
        // SET the field isFilled to isFilled
        this.isFilled = isFilled;
    }
 
    /**
     * Returns whether or not the bubble is filled.
     * 
     * @return A boolean representing whether or not the bubble is filled.
     */
    public boolean isFilled() {
        // RETURN isFilled
        return isFilled;
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
        // IF o is of type Bubble
            // READ isFilled of o as otherFilled
            //
            // IF isFilled and otherFilled are both true
                // RETURN true
            // ELSE
                // RETURN false
            // ENDIF
        // ELSE
            // RETURN false
        // ENDIF
        
        if (this.getClass() == o.getClass()) {
            Bubble other = (Bubble) o;
            
            return (isFilled == other.isFilled);
        }
        
        return false;
    }

    /**
     * Generates a hash code for the Bubble.
     * 
     * @return A hash code for the Bubble.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.isFilled ? 1 : 0);
        hash = 11 * hash + Objects.hashCode(this.label);
        return hash;
    }
}
