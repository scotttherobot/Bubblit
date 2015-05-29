package com.universalquantification.examgrader.models;

import java.util.List;

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
    private List<Bubble> choices;
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
    public Answer(List<Bubble> choices, int question)
    {
        // SET choices field to choices
        // SET number to question
        this.choices = choices;
        number = question;
    }
    
    /**
     * Constructs an answer that is a duplicate of another answer.
     * 
     * @param other The answer to create a duplicate of.
     */
    public Answer(Answer other) 
    {
        this.choices = other.choices;
        this.number = other.number;
    }
    
    /**
     * Checks if the answer has any bubbles filled in.
     * 
     * @return A boolean representing whether or not the answer is blank.
     */
    public boolean isEmpty() {
        for (Bubble b : choices) {
            if (b.isFilled()) return false;
        }
        
        return true;
    }
    
    /**
     * Returns the number of this answer.
     * 
     * @return The number for this question.
     */
    public int getNumber() {
        // RETURN number
        
        return number;
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

        // INIT returnValue to 0
        // READ choices of o as otherChoices 
        // READ number of o as otherNumber
        // 
        // IF number is less than otherNumber
            // SET returnValue to -1
        // ELSE IF number is greater than otherNumber
            // SET returnValue to 1
        // ENDIF
        //
        // IF returnValue is equal to 0
            // FOR each choice in choices
                // SET idx to the index of choice
                // READ the item in index idx of otherChoices as otherChoice
                // 
                // IF otherChoice and choice are not equal
                    // returnValue = 1
                    // ENDFOR
                // 
            // ENDFOR
        // ENDIF
        //
        // RETURN returnValue
        
        int returnValue = 0;
        List<Bubble> otherChoices = o.choices;
        int otherNumber = o.number;
        
        if (number < otherNumber) {
            returnValue = -1;
        }
        else if (number > otherNumber) {
            returnValue = 1;
        }
        
        if (returnValue == 0) {
            // get the first difference
            // get the second difference
           
            // take the one with the least label
            Object[] choicesArray = choices.toArray();
            Object[] otherChoicesArray = otherChoices.toArray();
            
            for (int idx = 0; idx < choicesArray.length; idx++) {
                if (!((Bubble) choicesArray[idx]).equals((Bubble) otherChoicesArray[idx])) {
                    returnValue = -1;
                    
                    break;
                }
            }
        }
        
        return returnValue;
    }
    
    /**
     * Returns a hash code for the object.
     * 
     * @return The hash code for the object
     */
    @Override
    public int hashCode()
    {
        return this.number + 31 * this.choices.hashCode(); 
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
        // IF o is of type Answer
            // READ choices of o as otherChoices 
            // READ number of o as otherNumber
            // 
            // IF number is not equal to otherNumber
                // RETURN false
            // ENDIF
            //
            // IF returnValue is equal to 0
                // FOR each choice in choices
                    // SET idx to the index of choice
                    // READ the item in index idx of otherChoices as otherChoice
                    // 
                    // IF otherChoice and choice are not equal
                        // RETURN false
                    // 
                // ENDFOR
            // ENDIF
            //
            // RETURN true
        // ELSE
            // RETURN false
        // ENDIF
        
        if (this.getClass() == o.getClass())
        {
            Answer other = (Answer) o;
            int otherNumber = other.number;
           
            return otherNumber == number && other.choices.equals(this.choices);
        }
        
        return false;
    }

    /**
     * Returns a string representation of this Answer.
     * 
     * @return A string representation of this answer. 
     */
    @Override
    public String toString()
    {
        // SET string to number followed by a ") "
        //
        // FOR each choice in choices
            // IF choice.isFilled
                // SET idx to the index of choice
                // SET character to 'A' plus idx
                //
                // CALL concat on string with character
            // ENDIF
        // ENDFOR
        
        String message = number + ") ";
        int index = 0;
        
        for (Bubble choice : choices) {
            if (choice.isFilled()) {
                String letter = Character.toString((char) (65 + index));
                
                message = message.concat(letter);
            }
            
            index++;
        }
        
        return message;
    }
}
