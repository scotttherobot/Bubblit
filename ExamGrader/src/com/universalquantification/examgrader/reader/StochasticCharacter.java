package com.universalquantification.examgrader.reader;

import java.util.HashMap;
import java.util.Map;

/**
 * A character that does not have a well-defined value,
 * but instead has an associated probability mass function.
 * For ease of construction and use,
 * the probabilities are not guaranteed to sum to unity.
 *
 * @author William Chargin
 * @version 15 May 2015
 */
public class StochasticCharacter
{

    /**
     * The probability masses for each character.
     */
    private final Map<Character, Double> probabilities = new HashMap<>();

    /**
     * Sets the probability that this character's true value
     * is the value of the given cahracter.
     *
     * @param character
     *      the candidate value
     * @param probability
     *      the probability that this value is correct
     */
    public void setProbability(char character, double probability)
    {
        probabilities.put(character, probability);
    }

    /**
     * Gets the probability that this character's true value
     * is the value of the given character.
     * If the character has never been explicitly set,
     * the probability is {@code 0.0}.
     *
     * @param character
     *      the candidate value
     * @return
     *      the probability that this value is correct
     */
    public double getProbability(char character)
    {
        Double probability = probabilities.get(character);

        // Default to 0.
        if (probability == null)
        {
            probability = 0.0;
        }

        return probability;
    }

    @Override
    public String toString()
    {
        /*
         * Returns the most likely value, or '-' if no values are set.
         */

        double maxProb = Double.NEGATIVE_INFINITY;
        char value = '-';

        // Check each candidate value.
        for (Character key : probabilities.keySet())
        {
            double prob = probabilities.get(key);

            // If this is the most likely so far, update the tracking fields.
            if (prob > maxProb)
            {
                maxProb = prob;
                value = key;
            }
        }

        return Character.toString(value);
    }

}
