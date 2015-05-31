package com.universalquantification.examgrader.reader;

/**
 * Holds information about a calibration region for an exam.
 * @author luis
 */
public class CalibrationBubbleRegion
{
    /**
     * The minimum fill ratio for bubbles that was found 
     */
    public final double minFillRatio;
    
    /**
     * The average fill ratio for bubbles that was found
     */
    public final double avgFillRatio;
    
    /**
     * Create a new CalibrationBubbleRegion
     * @param min the minimum fill ratio
     * @param avg the average fill ratio
     */
    public CalibrationBubbleRegion(double min, double avg)
    {
        this.minFillRatio = min;
        this.avgFillRatio = avg;
    }
}
