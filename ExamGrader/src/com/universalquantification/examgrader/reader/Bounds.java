package com.universalquantification.examgrader.reader;

/**
 * The Bounds class represents x and y bounds for an exam in an image.
 * @version 2.0
 * @author Luis
 */
class Bounds
{
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    /**
     * Get the minimum x-value for an exam in an image.
     * @return the x-value
     */
    public int getMinX()
    {
        return minX;
    }

     /**
     * Set the minimum x-value for an exam in an image.
     * @param minX the x-value
     */
    public void setMinX(int minX)
    {
        this.minX = minX;
    }

    /**
     * Get the maximum x-value for an exam in an image.
     * @return the x-value
     */
    public int getMaxX()
    {
        return maxX;
    }
     /**
     * Set the maximum x-value for an exam in an image.
     * @param maxX the x-value
     */
    public void setMaxX(int maxX)
    {
        this.maxX = maxX;
    }
    
    /**
     * Get the minimum y-value for an exam in an image.
     * @return the y-value
     */
    public int getMinY()
    {
        return minY;
    }
    
     /**
     * Set the minimum y-value for an exam in an image.
     * @param minY the y-value
     */
    public void setMinY(int minY)
    {
        this.minY = minY;
    }
    

    /**
     * Get the maximum y-value for an exam in an image.
     * @return the y-value
     */
    public int getMaxY()
    {
        return maxY;
    }
     /**
     * Set the maximum y-value for an exam in an image.
     * @param maxY the y-value
     */
    public void setMaxY(int maxY)
    {
        this.maxY = maxY;
    }
    
    /**
     * Outputs the min and max, x and y values
     * @return string representing min and max, x and y values.
     */
    @Override
    public String toString()
    {
        return "Bounds: [minX " + this.minX + "]"
            + "[maxX " + this.maxX + "]"
            + "[minY " + this.minY + "]"
            + "[maxY " + this.maxY + "]";
            
    }
}
