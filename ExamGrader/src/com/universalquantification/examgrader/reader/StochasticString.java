package com.universalquantification.examgrader.reader;

import java.util.ArrayList;
import java.util.List;

/**
 * A string of {@code StochasticCharacter}s,
 * each of whose value is not known with certainty.
 *
 * @author William Chargin
 * @version 15 May 2015
 */
public class StochasticString
{

    /**
     * The power to which to raise the termwise discrepancies
     * in the computation of the distance heuristic.
     */
    public static final double kExponentTermwise = 4;

    /**
     * The power to which to raise the length discrepancy
     * in the computation of the distance heuristic.
     */
    public static final double kExponentLength = 2;

    /**
     * The list of characters that this string comprises.
     */
    private final List<StochasticCharacter> characters = new ArrayList<>();

    /**
     * Adds the given character to the end of this string.
     *
     * @param character
     *      the character to add
     */
    public void append(StochasticCharacter character)
    {
        characters.add(character);
    }

    /**
     * Gets the number of characters in this string.
     *
     * @return
     *      the number of characters in this string
     */
    public int length()
    {
        return characters.size();
    }

    /**
     * Computes the value of the distance heuristic
     * for this stochastic string and the given reference string.
     *
     * @param reference
     *      the reference string for which to compute the heuristic value
     * @return
     *      a non-negative distance value (lower indicates a better match)
     */
    public double computeDistance(String reference)
    {
        double distance = 0;

        int maxIndex = Math.min(characters.size(), reference.length());

        // Consider the characters in sequence.
        for (int index = 0; index < maxIndex; index++)
        {
            char referenceChar = reference.charAt(index);
            StochasticCharacter actualChar = characters.get(index);
            double disagreement = 1 - actualChar.getProbability(referenceChar);
            distance += Math.pow(disagreement, kExponentTermwise);
        }

        int lengthDifference = reference.length() - characters.size();
        distance += 1 * Math.abs(Math.pow(lengthDifference, kExponentLength));

        return distance;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        // Delegate to each |StochasticCharacter|'s most likely value.
        for (StochasticCharacter character : characters)
        {
            sb.append(character);
        }

        return sb.toString();
    }

}
