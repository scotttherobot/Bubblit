package com.universalquantification.examgrader.reader;

/**
 * Describes a point on an image.
 * 
 */
public class ImagePoint
{
    private int pX;
    private int pY;

    /**
     * Get x coordinate
     * @return x coordinate
     */
    public int getpX()
    {
        return pX;
    }
    
    /**
     * Set x coordinate
     * @param px value of x coordinate
     */
    public void setpX(int px)
    {
        this.pX = px;
    }

    /**
     * Get y coordinate
     * @return y coordinate
     */
    public int getpY()
    {
        return pY;
    }

    /**
     * Set y coordinate
     * @param py value of y coordinate
     */
    public void setpY(int py)
    {
        this.pY = py;
    }
    
    /**
     * Image point to string
     * @return string representation
     */
    public String toString()
    {
        return "point [x: " + this.pX + ",y: " + this.pY+ "] ";
    }
    
}
