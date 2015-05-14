package com.universalquantification.examgrader.reader;

/**
 * Describes bounds for a letter in an image.
 *
 */
public class Bounds
{
    public final int minX;
    public final  int maxX;
    public final  int minY;
    public final  int maxY;
    public Bounds(int xMin, int xMax, int yMin, int yMax)
    {
        this.minX = xMin;
        this.maxX = xMax;
        this.minY = yMin;
        this.maxY = yMax;
    }
    public String toString()
    {
        return "Bounds: [minX " + this.minX + "]"
            + "[maxX " + this.maxX + "]"
            + "[minY " + this.minY + "]"
            + "[maxY " + this.maxY + "]";
            
    }
}
