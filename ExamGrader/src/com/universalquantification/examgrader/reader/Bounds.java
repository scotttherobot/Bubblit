package com.universalquantification.examgrader.reader;

/**
 * Describes bounds for a letter in an image.
 *
 */
public class Bounds
{
    /**
     * The minimum x coordinate
     */
    public final int minX;

    /**
     * The maximum y coordinate
     */
    public final int maxX;

    /**
     * The minimum y coordinate
     */
    public final int minY;

    /**
     * The maximum y coordinate
     */
    public final int maxY;

    /**
     * Create a new bounds object
     *
     * @param xMin the minimum x coordinate
     * @param xMax the minimum x coordinate
     * @param yMin the minimum y coordinate
     * @param yMax the maximum y coordinate
     */
    public Bounds(int xMin, int xMax, int yMin, int yMax)
    {
        this.minX = xMin;
        this.maxX = xMax;
        this.minY = yMin;
        this.maxY = yMax;
    }

    /**
     * Get the string representation for this object
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return "Bounds: [minX " + this.minX + "]"
            + "[maxX " + this.maxX + "]"
            + "[minY " + this.minY + "]"
            + "[maxY " + this.maxY + "]";

    }

    /**
     * Get minimum x coordinate
     *
     * @return min x coordinate
     */
    public int getMinX()
    {
        return minX;
    }

    /**
     * get maximum x coordinate
     *
     * @return max x coordinate
     */
    public int getMaxX()
    {
        return maxX;
    }

    /**
     * get minimum y coordinate
     *
     * @return min y coordinate
     */
    public int getMinY()
    {
        return minY;
    }

    /**
     * get maximum y coordinate
     *
     * @return max y coordinate
     */
    public int getMaxY()
    {
        return maxY;
    }
}
